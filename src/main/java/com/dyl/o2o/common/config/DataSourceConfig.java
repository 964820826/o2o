package com.dyl.o2o.common.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.dyl.o2o.common.split.DynamicDataSource;
import com.dyl.o2o.common.split.DynamicDataSourceHolder;
import com.dyl.o2o.common.split.DynamicDataSourceInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置类
 *
 * @author ：dyl
 * @date ：Created in 2019/11/16 10:53
 */
@Configuration //标明是配置类，将此类实例化到spring容器中
@MapperScan(basePackages = "com.dyl.o2o.dao*")
public class DataSourceConfig {

    /**
     * 将主数据源配置到spring容器中
     *
     * @return
     */
    @Bean(name = "masterDataSource")
//    @Primary //设置为默认数据源
    @ConfigurationProperties(prefix = "spring.datasource.master") //读取yml配置文件中前缀为spring.datasource.master的参数映射为对象
    public DataSource getMasterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 将从数据源配置到spring容器中
     *
     * @return
     */
    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave") //读取yml配置文件中前缀为spring.datasource.master的参数映射为对象
    public DataSource getSlaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置动态数据源
     * @param masterDataSource
     * @param salveDataSource
     * @return
     */
    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource getDynamicDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                        @Qualifier("slaveDataSource") DataSource salveDataSource){
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceHolder.DB_MASTER, masterDataSource);
        targetDataSources.put(DynamicDataSourceHolder.DB_SLAVE, salveDataSource);
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //将master和slave数据源作为目标数据源
        dynamicDataSource.setTargetDataSources(targetDataSources);
        //将master数据源作为默认数据源
//        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    @Bean
    public DynamicDataSourceInterceptor dynamicDataSourceInterceptor(){
        return new DynamicDataSourceInterceptor();
    }
    /**
     * 将动态数据源配置到sql会话工厂
     *
     * @return
     */
    @Bean(name = "sqlSessionFactory")
    //@Qualifier表示在spring容器中查找名为dynamicDataSource的对象
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        //配置mybatis,对应mybatis-config.xml
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        //懒加载
        LazyConnectionDataSourceProxy proxy = new LazyConnectionDataSourceProxy();
        //设置目标数据源
        proxy.setTargetDataSource(getDynamicDataSource(getMasterDataSource(),getSlaveDataSource()));
        sqlSessionFactory.setDataSource(proxy);
        //需要mapper文件时加入扫描
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapping/*Mapper.xml"));
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        //加入上面两个拦截器
        Interceptor[] interceptor = {paginationInterceptor(), dynamicDataSourceInterceptor()};
        sqlSessionFactory.setPlugins(interceptor);
        return sqlSessionFactory.getObject();

//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        //设置数据源,如果没有将 dynamicDataSource 作为数据源则不能实现切换
//        bean.setDataSource(dynamicDataSource);
//        bean.setPlugins(new DynamicDataSourceInterceptor());
//        //设置mybatis的xml所在位置
////        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
//        return bean.getObject();
    }

//    @Bean(name = "dataSource")
//    public LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource){
//        LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy = new LazyConnectionDataSourceProxy();
//        lazyConnectionDataSourceProxy.setTargetDataSource(dynamicDataSource);
//        return lazyConnectionDataSourceProxy;
//    }

//    /**
//     * 主数据库的模板
//     *
//     * @param sqlSessionFactory
//     * @return
//     */
//    @Bean(name = "masterSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }

    /**
     * 配置事务管理,在需要使用事务的地方加上@Transactional注解
     * @param dynamicDataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(DynamicDataSource dynamicDataSource){
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}
