package top.mylady.admin.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 配置相关
 */
@Configuration
@ComponentScan("top.mylady.common.mysql.core")  //mysql初始化扫描配置类
@MapperScan("top.mylady.admin.dao")
public class MysqlConfig {


}
