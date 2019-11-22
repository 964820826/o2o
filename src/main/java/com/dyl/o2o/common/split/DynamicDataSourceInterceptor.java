package com.dyl.o2o.common.split;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * 拦截mybatis传来的sql信息,分配数据源
 *
 * @author ：dyl
 * @date ：Created in 2019/11/11 23:02
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {

    //正则表达式,用于判断是不是增删改的sql(u0020代表空格)
    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    /**
     * 对拦截的sql进行的具体操作
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        //判断是不是事务(被@Transaction注解的方法)
        boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
        //获取mybatis转换的sql中的变量参数
        //第一个参数携带操作的标志(决定是增删改查的哪一个),第二个参数是sql
        Object[] objects = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) objects[0];
        String lookupKey = DynamicDataSourceHolder.DB_MASTER;

        if (synchronizationActive != true) {//不是事务管理
            //读方法
            if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                //若selectKey为自增id查询主键方法,就使用主库(即mybatis调用SELECT LAST_INSERT_ID()的方法,插入数据返回主键的useGeneratorKey就调用此方法)
                //用在插入店铺,然后返回店铺id,再用此id更新店铺图片,此时希望查询时在主库中查询;本项目修改了保存图片的逻辑,此处无用
                if (mappedStatement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    lookupKey = DynamicDataSourceHolder.DB_MASTER;
                } else {
                    //获取sql
                    BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(objects[1]);
                    //将制表符,换行符,回车替换为空格
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replace("[\\t\\n\\r]", " ");
                    //若是增删改的语句,使用主库
                    if (sql.matches(REGEX)) {
                        lookupKey = DynamicDataSourceHolder.DB_MASTER;
                    } else {
                        lookupKey = DynamicDataSourceHolder.DB_SLAVE;
                    }
                }
            }
        } else {
            //事务管理一般都是写数据,使用主库
            lookupKey = DynamicDataSourceHolder.DB_MASTER;
        }
        log.info("设置方法[{}] use [{}] Strategy,SqlCommandType [{}]..", mappedStatement.getId(), lookupKey, mappedStatement.getSqlCommandType().name());
        //设置要使用的数据源
        DynamicDataSourceHolder.setDBType(lookupKey);
        return invocation.proceed();
    }

    /**
     * 指定哪些方法被此拦截器拦截
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        //若target是增删改查操作,就将其拦截,将intercept包装到target里,决定用哪个数据源,否则返回本体
        //Executor用来支持增删改查
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }else {
            return target;
        }
    }

    /**
     * 类初始化时要做的相关配置(不必要)
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
