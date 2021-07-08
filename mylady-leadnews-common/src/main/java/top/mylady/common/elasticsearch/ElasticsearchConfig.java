package top.mylady.common.elasticsearch;


import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import lombok.Data;
import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Data
@Configuration
@ConfigurationProperties(prefix="spring.elasticsearch.jest")
@PropertySource("classpath:elasticsearch.properties")
public class ElasticsearchConfig {

    private static final Logger logger = Logger.getLogger(ElasticsearchConfig.class);

    private String url;
    private Integer readTimeout;
    private Integer connectionTimeout;

    @Bean
    public JestClient getJestClient(){
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(this.url)
                .multiThreaded(true)
                .connTimeout(this.connectionTimeout)
                .readTimeout(this.readTimeout)
                .build()
        );
        logger.info("Common, ElasticSearch初始化完毕, 交给Spring容器管理");
        return  factory.getObject();

    }
    /*
    * 使用
    * 1, 配置扫描
    *
    * @Configuration
    * @ComponentScan("top.mylady.elasticsearch")
    * public class EsConfig{}
    *
    * 2, 使用
    * @Autowired
    * private JestClient jestClient;
    *
    * */

}
