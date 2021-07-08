package top.mylady.common.mysql.core;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;


@Getter
@Setter
@Configuration  //声明为spring配置类
@ConfigurationProperties(prefix="mysql.core")  //设置前缀
@PropertySource("classpath:mysql-core-jdbc.properties")  //引入配置文件
@MapperScan(basePackages="top.mylady.model.mappers", sqlSessionFactoryRef="mysqlCoreSessionFactory")  //扫描接口创建代理对象
public class MysqlCoreConfig {

    String jdbcUrl;
    String jdbcUserName;
    String jdbcPassword;
    String jdbcDriver;
    String rootMapper;      //mapper文件在classpath下存放的根路径
    String aliasesPackage;  //别名包

    private static Logger logger = Logger.getLogger(MysqlCoreConfig.class);

    /**
     * 设置一个数据库的连接池
     */
    @Bean("mysqlCoreDataSource")
    public DataSource mysqlCoreDataSource(){
        HikariDataSource dataSource = new HikariDataSource();

        //dataSource.setUsername(this.getJdbcUserName());
        //dataSource.setPassword(this.getRealPassword());  //获取到的是配置文件中反转的秘密, 感觉多此一举
        //dataSource.setJdbcUrl(this.getJdbcUrl());
        //dataSource.setDriverClassName(this.getJdbcDriver());

        //&useSSL=true
        String url_1 = "jdbc:mysql://192.168.0.107:3306/leadnews?serverTimezone=UTC&useSSL=true";
        String url_2 = "jdbc:mysql://116.62.101.170:3306/heima_leadnews?serverTimezone=UTC&useSSL=true";
        String alyun_myCat = "jdbc:mysql://116.62.101.170:8066/mycat_leadnews?autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        String LocalHostMyCat = "jdbc:mysql://192.168.0.107:8066/mycat_leadnews?autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";

        dataSource.setUsername("root");
        dataSource.setPassword("YING123ZZ");
        dataSource.setJdbcUrl(alyun_myCat);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        //dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        //最大连接数
        dataSource.setMaximumPoolSize(50);
        //最小连接数
        dataSource.setMinimumIdle(5);
        logger.info(String.format("数据库dataSource加载完毕, 交给Spring管理, 现在的连接对象是: %s", dataSource.getJdbcUrl().substring(0, 60)));
        return dataSource;
    }

    /**
     * 密码反转操作
     */
    public String getRealPassword(){
        String jdbcPassword = this.getJdbcPassword();  //123456
        String reverse = StringUtils.reverse(jdbcPassword);  //654321
        return  reverse;
    }

    /**
     * 整合mybatis  SqlSessionFactoryBean
     * `@Qualifier("mysqlCoreDataSource") DataSource dataSource -> 配合注入 dataSource
     */
    @Bean("mysqlCoreSessionFactory")
    public SqlSessionFactoryBean mysqlCoreSessionFactory(@Qualifier("mysqlCoreDataSource") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //数据源
        factoryBean.setDataSource(dataSource);
        //别名
        factoryBean.setTypeAliasesPackage(this.getAliasesPackage());  //设置包别名

        //mapper文件存储的位置
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        String path = getMapperFilePath();
        logger.debug(String.format("打印生成的getMapperFilePath: %s", path));

        Resource[] resources = resolver.getResources(path); //"classpath:mappers/*/*.xml"
        factoryBean.setMapperLocations(resources);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factoryBean.setConfiguration(configuration);
        logger.info("mysqlCoreSessionFactory加载完毕, 交给Spring管理");
        return factoryBean;
    }

    /**
     * 拼接mapper.xml文件的存储位置
     */
    public String getMapperFilePath(){
        String sqlPath = new StringBuffer("classpath:").append(this.getRootMapper()).append("/*/*.xml").toString();
        return sqlPath;
    }
}
