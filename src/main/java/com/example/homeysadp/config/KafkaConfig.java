package com.example.homeysadp.config;


import com.example.homeysadp.model.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name("order-created")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderAssignedTopic() {
        return TopicBuilder.name("order-assigned")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderStatusTopic() {
        return TopicBuilder.name("order-status")
                .partitions(1)
                .replicas(1)
                .build();
    }
}