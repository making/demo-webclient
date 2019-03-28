package com.example.demowebclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class DemoWebclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWebclientApplication.class, args);
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(50);
        taskExecutor.setMaxPoolSize(1440); // 30 (server.tomcat.max-threads) * 48
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setThreadNamePrefix("foo-");
        return taskExecutor;
    }
}
