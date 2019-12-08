个人学习项目
------------------
-此项目属于伪前后端分离，页面跳转会经由controller层控制，且前端代码与后端代码在同一个项目中保存。但后端接口使用
Restful风格编写，且使用swagger提供对外的接口调用

## 主要技术选用
### 集成开发环境：intellij idea 2019.2.1
	主流开发工具有idea和eclipse，但idea在代码提示，导包，开发便利上讲更有优势，所以选用idea
### 开发工具包：JDK8
	近期新出现很多新的JDK版本，但是JDK8已经经过市场考验许多年了，出现问题也相对好找解决办法，是当前最主流的开发工具包，
	故选用JDK8
### 数据库：MySQL8
	在性能和安全方面比之前的5.7版本有所加强，如今越来越多的人开始使用8.0版本，顺应时代潮流
### 数据库管理工具：Navicat12.1
	管理MySQL的工具里Navicat使用较为主流
### 持久层框架：Mybatis-plus 3.2.0
    目前最主流的持久层框架为mybatis，但是配置xml较繁琐且重复工作较多。mybatis-plus对mybatis只做增强，
    不做修改，还能自动生成代码，可以大大减少简单重复的工作
### 前端框架：Thymeleaf
    SpringBoot官方推荐前端框架
### 应用开发框架：SpringBoot 2.1.8.RELEASE
    免去了复杂的xml配置，约定优先于配置，可以更快速的搭建起项目，易用性更好
### 开发插件：lombok
    用注解形式简化代码，节省开发时间
### 日志框架：抽象层：SLF4j 实现层：Logback
    springboot默认使用Logback
### 安全框架：SpringSecurity 5.1.5
    SpringSecurity相比Shiro而言，功能相对丰富，且与spring配合更方便，缺点是相对复杂
    Shiro可以脱离spring使用，使用简单，虽说足以满足本项目的使用需求，但为了在与spring整合过程中尽量少踩坑，本着学习spring全家桶的目的，使用springSequire


# 开发中要点记录
## 数据库设计：
#### 数据表统一前缀为tb_
	因数据表数量较少，故不使用前缀分类，前缀统一
- 数据库引擎使用InnoDB

    	MYISAM是表级锁，操作一条记录时整个表不允许其他数据修改。InnoDB是行级锁，当需要有事务支持时需选用InnoDB
    	
- 数据表主键为自动递增

	    id仅作为唯一标识使用，无其他含义，故自增即可
	
- 数据表中记录时间使用datetime，且时间自动更新，不需在程序中设置时间

	    datetime相比timestamp时间跨度更大，而且使用范围是中国，不需要调整时区，故选用datetime
	
- 将person作为登陆的个体，围绕PersonDo有微信授权类（WechatAuthDo）本地账户类（LocalAuthDo）

- 表关系使用外键关联

        因数据库数据表及数据量较小，为确保数据的关联、明确表之间的关系，使用外键
    
- 数据库中存储图片存储的是相对路径
    
        为了确保项目移植后还可以读取到图片信息，故使用相对路径的方式保存图片地址



## 项目结构设计：
   
### controller层设计
- 因前后端未完全分离，前端页面跳转需经过controller层处理，但不想让跳转方法暴露到swagger中，故单独将用于跳转的controller放在pageJump包中
- controller中不处理业务层中出现的异常，直接抛出由全局异常处理器处理
- 后端给前端返回类型统一使用R类包装返回，R中包含程序执行的状态码和状态消息和所需数据，状态码与状态消息使用枚举类ResultCode确保唯一性
----------------------------------------------
### service层设计
- service层方法命名参考mybatisplus中提供的service层方法来命名
- service中的方法只供congtroller层调用，不对外开放，故不对参数进行校验
- service层中进行事务控制
- 查询条件构造放在业务层中
----------------------------------------------
### dao层设计
- 数据库基础查询使用mp中的模板方法，复杂查询使用mybatis，通过mapping包中的xml文件操作数据库
- Do包中存放数据库中每个数据表对应的实体类
- 实体类中的变量类型统一使用引用类型

        若使用基本数据类型，在创建对象时会给变量初始化一个默认值，但我们希望变量的值都是我们主动赋予的，故使用引用类型
----------------------------------------------
### 实体类    
- 实体类中关联到其他类时只指向其id
  
        mybatisPlus对于复合类型的插入及查询需要自定义sql，这样就失去了mp的便利性，故统一使用id作为关联，不在实体类中套实体类，操作数据表时也是针对单一实体类操作，逻辑清晰，且可以减少大量xml的编写。（当需要用到实体类中关联实体类的信息，则通过关联的id再      执行查询，相比编写xml要方便些）
        
- 凡是会出现在swagger中的实体类全部用swagger的注解来标明字段含义
    
        用swagger注解标注含义可以在接口文档中直观的显示出字段含义，查看源码时也一目了然，不需要再通过注释来标注字段含义 


## 其他
#### 日志存放到项目根目录，按照级别，日期分开存放

- 开发优先级为：店家管理>前端展示>超级管理员系统
- 项目中查询店铺列表使用逻辑分页，查询商品列表使用物理分页

        因数据总量较少，且数据不经常变化，所以查询一次数据库，将结果保存到session中，翻页时直接从session中取数据，不需要
        多次查询数据，提高查询效率，减少数据库查询压力
        但为熟悉两种不同分页方式的编写，故商品页的查询使用物理分页，每次获取数据都需要从数据库中查询，通过sql中limit限制获取到的数量