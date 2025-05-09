package org.beikei.design.config;

import org.dromara.dynamictp.core.support.DynamicTp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class MyThreadPoolConfiguration {

    @Bean
    @DynamicTp("myThreadPool")
    public ThreadPoolTaskExecutor myThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(0);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("bk-me-");
        executor.setAllowCoreThreadTimeOut(false);
//        executor.setQueueCapacity(0);
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setQueueCapacity(100);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
