package com.dag.nexq_userservice;

import com.dag.nexq_userservice.config.GoogleAuthConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GoogleAuthConfig.class)
public class NexQUserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexQUserserviceApplication.class, args);
    }

}
