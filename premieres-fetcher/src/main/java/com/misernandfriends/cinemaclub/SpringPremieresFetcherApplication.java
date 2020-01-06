package com.misernandfriends.cinemaclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringPremieresFetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPremieresFetcherApplication.class, args);
    }
}
