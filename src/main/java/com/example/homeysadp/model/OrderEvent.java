package com.example.homeysadp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String orderId;
    private String chefId;
    private String customerId;
    private Map<String, Integer> items; // dishId -> quantity
    private OrderStatus status;
    private LocalDateTime timestamp;
    private double totalAmount;
    private String deliveryAddress;
}

