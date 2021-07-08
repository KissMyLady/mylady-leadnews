# 项目名称: 黑马头条
=========


## 版本说明
> 1, 创建时间: 2021连6月18日    
> 2, 笔记地址: https://blog.mylady.top/category/7


## 各个模块说明
后端工程基于Spring-boot 2.1.5.RELEASE 版本构建，工程父项目为heima-leadnews，并通过继承方式集成Spring-boot。

[父项目下分4个公共子项]：
- heima-leadnews-common : 是整个工程的配置核心，包括所有集成三方框架的配置定义，比如redis、kafka等。除此之外还包括项目每个模块及整个项目的常量定义;      
- heima-leadnews-model ：项目中用到的Dto、Pojo、Mapper、Enums定义工程;      
- heima-leadnews-utils : 工程公用工具类项目，包含加密/解密、Date、JSON等工具类;      
- heima-leadnew-apis : 整个项目微服务暴露的接口的定义项目，按每个模块进行子包拆分;      

[多个微服务]：
- heima-leadnews-login：用于实现APP+自媒体端用户的登录与注册功能；      
- heima-leadnews-user：用于实现APP端用户中心的功能，比如我的收藏、我的粉丝等功能；      
- heima-leadnews-article：用于实现APP端文章的获取与搜索等功能；还包括频道、标签等功能；      
- heima-leadnews-behavior：用于实现APP端各类行为数据的上传服务；      
- heima-leadnews-webmagic：用于实现文章数据的自动化爬取功能；      
- heima-leadnews-quartz：用于封装项目中所有的调度计算任务；  X    
- heima-leadnews-wemedia：用于实现自媒体管理端的功能；       
- heima-leadnews-admin：用于实现后台管理系统的功能；      
- service-gateway：spring cloud 网关       X
- service-eureka：spring cloud 注册中心    X  

备注: 
上述模块打X的代表教学带是没有的, 残缺的教学带(恶心的资料贩子)

## 通用Dev环境说明
多环境切换, 在每一个微服务的工程中的根目录下有三个文件, 方便各个环境的切换      
(1), maven_dev.properties     
>> 定义开发环境的配置     

(2), maven_prod.properties     
>> 生产环境     

(3), maven_test.properties       
>> 测试环境, 开发阶段使用这个测试环境       
    
打包:      
在打包的过程中也可以指定参数打包 package -P test|prod|dev       

### Mysql与MyBatis配置
通用配置:　
> mysql.core.jdbc.url=jdbc:mysql://192.168.0.107:3306/leadnews?autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai　　　　　
> mysql.core.jdbc.username=root　　　　　
> mysql.core.jdbc.password=XXX
> mysql.core.jdbc.driver=com.mysql.jdbc.Driver　　　　　
> 　　　　　
> mysql.core.root.mapper=mappers　　　　　
> mysql.core.aliases.package=top.mylady.model.**　　　　　
> mysql.core.tx.scan.package=execution(* top.mylady..service.*.*(..))　　　　　
　


## jackson封装解决字段序列化反虚拟化
主要的类包括: 
- IdEncrypt              自定义注解, 作用在`需要混淆的字段`属性上, 用于非id的属性上 在model包下
- DateDeserializer       用于处理`日期`输入反序列化 
- DateSerializer         用于日期的序列化输出 
- ConfusionSerializer    用于`序列化自增数字`的混淆
- ConfusionDeserializer  用于`反序列化自增数字`的混淆解密
- ConfusionSerializerModifier   用于`过滤`序列化时处理的字段
- ConfusionDeserializerModifier 用于`过滤`反序列化时处理的字段
- ConfusionModule    用于注册模块和修改器
- InitJacksonConfig  提供自动化配置默认ObjectMapper，让整个框架自动处理日期和id混淆



## 技术栈
<img src="https://blog.mylady.top/static/images/blog_sku_img/3-5.png">



## 其他描述
- 运用 Spring Boot快速开发框架，构建项目工程；并结合Spring Cloud全家桶技术，实现后端个人中心、自媒体、管理中心等微服务
- 运用 WebMagic爬虫技术，完善系统内容自动化采集
- 运用 Kafka完成内部系统消息通知；与客户端系统消息通知；以及实时数据计算
- 运用 MyCat数据库中间件计算，对系统数据进行分开分表，提升系统数据层性能
- 运用 Redis缓存技术，实现热数据的计算，NoSession等功能，提升系统性能指标
- 运用 Zookeeper技术，完成大数据节点之后的协调与管理，提升系统存储层高可用
- 使用 MySql存储用户数据，以保证上层数据查询的高性能
- 使用 Mongo存储用户热数据，以保证用户热数据高扩展和高性能指标
- 使用 FastDFS作为静态资源存储器，在其上实现热静态资源缓存、淘汰等功能
- 运用 Habse技术，存储系统中的冷数据，保证系统数据的可靠性
- 运用 ES搜索技术，对冷数据、文章数据建立索引，以保证冷数据、文章查询性能
- 运用 Sqoop、Kettle等工具，实现大数据的离线入仓；或者数据备份到Hadoop
- 运用 Spark+ Hive进行离线数据分析，实现系统中各类统计报表
- 运用 Spark Streaming + Hive+Kafka实现实时数据分析与应用；比如文章推荐
- 运用 Neo4j知识图谱技术，分析数据关系，产出知识结果，并应用到上层业务中，以帮助用户、自媒体、运营效果/能力提升。比如粉丝等级计算
- 运用 AI技术，来完成系统自动化功能，以提升效率及节省成本。比如实名认证自动化
