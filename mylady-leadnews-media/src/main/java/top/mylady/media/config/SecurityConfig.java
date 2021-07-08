package top.mylady.media.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ServletComponentScan("top.mylady.common.web.wm.security")
public class SecurityConfig {
}
