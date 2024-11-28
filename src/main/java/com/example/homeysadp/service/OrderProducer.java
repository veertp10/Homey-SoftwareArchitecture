package com.example.homeysadp.service;

import com.example.homeysadp.model.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderProducer {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrder(OrderEvent orderEvent) {
        try {
            CompletableFuture<SendResult<String, OrderEvent>> future =
                    kafkaTemplate.send("order-created", orderEvent.getOrderId(), orderEvent);

            future.thenAccept(result -> {
                log.info("Order published successfully: {}", orderEvent.getOrderId());
            }).exceptionally(ex -> {
                log.error("Failed to publish order: {}", ex.toString());
                return null;
            });
        } catch (Exception e) {
            log.error("Error publishing order: {}", e.toString());
            throw new RuntimeException("Failed to publish order", e);
        }
    }
}