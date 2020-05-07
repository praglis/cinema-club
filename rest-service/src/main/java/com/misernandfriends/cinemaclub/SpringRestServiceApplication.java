package com.misernandfriends.cinemaclub;

import com.misernandfriends.cinemaclub.config.CustomProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(CustomProperties.class)
@EnableCaching
@EnableAsync
public class SpringRestServiceApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringRestServiceApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringRestServiceApplication.class, args);
    }
}
