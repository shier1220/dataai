package com.dataai.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.dataai.query", "com.dataai.common"})
@EnableDiscoveryClient
public class DataaiQueryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataaiQueryApplication.class, args);
    }
}