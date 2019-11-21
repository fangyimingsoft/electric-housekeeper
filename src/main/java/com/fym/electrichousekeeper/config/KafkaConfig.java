package com.fym.electrichousekeeper.config;

import com.fym.electrichousekeeper.core.KafkaMessageListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    @ConditionalOnProperty(name="spring.kafka.active",havingValue = "true")
    public KafkaMessageListener messageListener(){
        return new KafkaMessageListener();
    }
}
