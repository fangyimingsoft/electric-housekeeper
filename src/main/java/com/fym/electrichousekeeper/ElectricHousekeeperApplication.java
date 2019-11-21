package com.fym.electrichousekeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
        scanBasePackages = "com.fym.electrichousekeeper"
)
@EnableJpaRepositories(basePackages = "com.fym.electrichousekeeper.**.dao")
@EnableKafka
@EnableScheduling
public class ElectricHousekeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectricHousekeeperApplication.class, args);
    }

}
