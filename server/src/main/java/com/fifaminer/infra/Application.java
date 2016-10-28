package com.fifaminer.infra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan("com.fifaminer")
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableMongoRepositories(basePackages = "com.fifaminer.repository" )
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
