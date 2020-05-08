package com.misernandfriends.cinemaclub.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@Slf4j
public class AsyncConfiguration {
    @Bean(name = "ratingLoaderExecutor")
    public Executor ratingLoaderExecutor() {
        log.debug("Creating async Rating Loader Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(200);
        executor.setThreadNamePrefix("RatingLoader-");
        executor.initialize();
        return executor;
    }
}
