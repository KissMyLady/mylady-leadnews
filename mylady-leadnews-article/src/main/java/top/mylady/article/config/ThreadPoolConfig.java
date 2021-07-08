package top.mylady.article.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


@Configuration
@EnableAsync
public class ThreadPoolConfig {

    private static final Logger logger = Logger.getLogger(ThreadPoolConfig.class);

    private static final int corePoolSize = 10;          // 核心线程数（默认线程数）
    private static final int maxPoolSize = 100;          // 最大线程数
    private static final int keepAliveTime = 10;         // 允许线程空闲时间（单位：默认为秒）
    private static final int queueCapacity = 500;        // 缓冲队列数
    private static final String threadNamePrefix = "Article-myladyLeadnews-async"; // 线程池名前缀

    /**
     * 默认异步线程池
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setThreadNamePrefix(threadNamePrefix);
        pool.setCorePoolSize(corePoolSize);
        pool.setMaxPoolSize(maxPoolSize);
        pool.setKeepAliveSeconds(keepAliveTime);
        pool.setQueueCapacity(queueCapacity);
        // 直接在execute方法的调用线程中运行
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        pool.initialize();
        logger.info("Article模块, 默认异步线程池完成, 交给Spring容器管理");
        return pool;
    }
}
