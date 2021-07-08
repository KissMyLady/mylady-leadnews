package top.mylady.common.zookeeper;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "zk")
@PropertySource("classpath:zookeeper.properties")
public class ZkConfig {

    private static final Logger logger = Logger.getLogger(ZkConfig.class);

    String host;
    String sequencePath;

    @Bean
    public ZookeeperClient zookeeperClient(){
        logger.info("创建zookeeper客户端, 返回给Spring容器管理");
        return new ZookeeperClient(this.host,this.sequencePath);
    }

}
