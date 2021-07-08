package top.mylady.media.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"top.mylady.common.common.init"})  //, "top.mylady.common.fastdfs"
public class InitConfig {
}
