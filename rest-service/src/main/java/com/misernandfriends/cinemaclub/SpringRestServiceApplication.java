package com.misernandfriends.cinemaclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
public class SpringRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestServiceApplication.class, args);
    }
}
