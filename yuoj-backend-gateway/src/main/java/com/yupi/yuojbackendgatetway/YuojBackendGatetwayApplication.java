package com.yupi.yuojbackendgatetway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// 排除mybatisPlus影响
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class YuojBackendGatetwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuojBackendGatetwayApplication.class, args);
    }

}
