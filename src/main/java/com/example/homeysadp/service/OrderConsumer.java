package com.example.homeysadp.service;

import com.example.homeysadp.model.OrderEvent;
import com.example.homeysadp.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderConsumer {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderConsumer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order-created", groupId = "homey-order-group")
    public void consumeOrder(OrderEvent orderEvent) {
        try {
            log.info("Received order event: {}", orderEvent.getOrderId());

            // Simulate order processing
            orderEvent.setStatus(OrderStatus.VALIDATED);

            // Publish to order-assigned topic
            CompletableFuture<SendResult<String, OrderEvent>> future =
                    kafkaTemplate.send("order-assigned", orderEvent.getOrderId(), orderEvent);

            future.thenAccept(result -> {
                log.info("Order assigned successfully: {}", orderEvent.getOrderId());
            }).exceptionally(ex -> {
                log.error("Failed to assign order: {}", ex.toString());
                return null;
            });

        } catch (Exception e) {
            log.error("Error processing order: {}", e.toString());
            // Handle error - maybe publish to dead letter queue
            orderEvent.setStatus(OrderStatus.CANCELLED);
            kafkaTemplate.send("order-status", orderEvent.getOrderId(), orderEvent);
        }
    }
}
