package com.kenzie.appserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}


//Note:  If u add @SpringBootApplication annotation to the class, you do not need to add
//the @EnableAutoConfiguration, @ComponentScan and @SpringBootConfiguration annotation
