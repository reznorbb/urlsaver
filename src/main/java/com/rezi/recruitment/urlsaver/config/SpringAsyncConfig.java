package com.rezi.recruitment.urlsaver.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class SpringAsyncConfig {

    @Value("${url-database-document-saver.executor.thread.core-pool}")
    private int corePoolSize;

    @Value("${url-database-document-saver.executor.thread.max-pool}")
    private int maxPoolSize;

    @Value("${url-database-document-saver.executor.queue.capacity}")
    private int queueCapacity;

    @Bean
    @Qualifier("urlDatabaseDocumentSaverExecutor")
    public ThreadPoolTaskExecutor urlDatabaseDocumentSaverExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("Async-");
        executor.afterPropertiesSet();
        return executor;
    }

}
