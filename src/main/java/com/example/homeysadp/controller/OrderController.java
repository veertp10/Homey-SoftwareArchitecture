package com.example.homeysadp.controller;


import com.example.homeysadp.model.CustomerOrder;
import com.example.homeysadp.model.OrderEvent;
import com.example.homeysadp.model.OrderStatus;
import com.example.homeysadp.service.OrderProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {
    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CustomerOrder customerOrder) {
        try {
            OrderEvent orderEvent = OrderEvent.builder()
                    .orderId(UUID.randomUUID().toString())
                    .customerId(customerOrder.getCustomerId())
                    .items(customerOrder.getItems())
                    .status(OrderStatus.CREATED)
                    .timestamp(LocalDateTime.now())
                    .deliveryAddress(customerOrder.getDeliveryAddress())
                    .build();

            orderProducer.publishOrder(orderEvent);
            return ResponseEntity.ok("Order created with ID: " + orderEvent.getOrderId());
        } catch (Exception e) {
            log.error("Error creating order: ", e);
            return ResponseEntity.internalServerError().body("Error processing order");
        }
    }
}