package org.mpo.newstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class NewsTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsTrackerApplication.class, args);
    }


    @Bean
    public Executor asyncExecutor() {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        return executor;
    }


}
