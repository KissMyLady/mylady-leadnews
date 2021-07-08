package top.mylady.article.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("top.mylady.common.elasticsearch")
public class EsConfig {

    /*
    * 当前测试类在 Article下的test包下
    * */
}
