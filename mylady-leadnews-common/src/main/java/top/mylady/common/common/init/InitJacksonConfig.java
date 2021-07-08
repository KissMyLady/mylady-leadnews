package top.mylady.common.common.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import top.mylady.common.jackson.ConfusionModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
* 提供自动化配置和默认ObjectMapper, 让整个框架自动处理日期和id混淆
* */
@Configuration
public class InitJacksonConfig {

    private static final Logger logger = Logger.getLogger(InitJacksonConfig.class);

    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();

        //注册模块,
        objectMapper = ConfusionModule.registerModule(objectMapper);
        logger.debug("Common init -> 提供自动化配置哦默认ObjectMapper, 让整个框架自动处理日期和id混淆. 注册成功, 交给Spring容器管理");
        return objectMapper;
    }

}
