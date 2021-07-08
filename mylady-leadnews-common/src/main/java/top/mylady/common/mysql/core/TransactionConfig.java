package top.mylady.common.mysql.core;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;


@Setter
@Getter
@Aspect  //声明为切面
@EnableAspectJAutoProxy  //开启自动代理
@EnableTransactionManagement  //开启当前的事务管理
@Configuration
@ConfigurationProperties(prefix="mysql.core")
@PropertySource("classpath:mysql-core-jdbc.properties")
public class TransactionConfig {

    private static Logger logger = Logger.getLogger(TransactionConfig.class);

    String txScanPackage;

    /**
     * 初始化事务管理器
     * @param dataSource dataSource
     * @return Spring容器管理对象
     */
    @Bean
    public DataSourceTransactionManager mysqlCoreDataSourceTransactionManager(@Qualifier("mysqlCoreDataSource") DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    /**
     * 设置事务拦截器
     * @param dataSourceTransactionManager 数据管理对象
     * @return Spring容器对象
     */
    @Bean("mysqlCoreDataSourceTxAdvice")
    public TransactionInterceptor mysqlCoreDataSourceTxAdvice(@Qualifier("mysqlCoreDataSourceTransactionManager") DataSourceTransactionManager dataSourceTransactionManager) {
        // 默认事务
        DefaultTransactionAttribute defAttr = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
        // 查询只读事务
        DefaultTransactionAttribute queryAttr = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
        queryAttr.setReadOnly(true);  //在查询的时候, 设置只读
        // 设置拦截的方法
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("save*", defAttr);
        source.addTransactionalMethod("insert*", defAttr);
        source.addTransactionalMethod("delete*", defAttr);
        source.addTransactionalMethod("update*", defAttr);
        source.addTransactionalMethod("exec*", defAttr);
        source.addTransactionalMethod("set*", defAttr);
        source.addTransactionalMethod("add*", defAttr);

        source.addTransactionalMethod("get*", queryAttr);
        source.addTransactionalMethod("query*", queryAttr);
        source.addTransactionalMethod("find*", queryAttr);
        source.addTransactionalMethod("list*", queryAttr);
        source.addTransactionalMethod("count*", queryAttr);
        source.addTransactionalMethod("is*", queryAttr);

        logger.info("事务拦截器设置完成, 交给Spring容器管理");
        return new TransactionInterceptor(dataSourceTransactionManager, source);
    }


    /*
    * 切面, 通知和切点
    * */
    @Bean
    public Advisor txAdviceAdvisor(@Qualifier("mysqlCoreDataSourceTxAdvice") TransactionInterceptor mysqlCoreDataSourceTxAdvice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(txScanPackage);
        logger.info("事务AOP[Aspect Oriented Programming]切面注入完成");
        return new DefaultPointcutAdvisor(pointcut, mysqlCoreDataSourceTxAdvice);
    }

}