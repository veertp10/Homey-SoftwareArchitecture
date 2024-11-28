package com.example.homeysadp.model;

import lombok.Data;
import java.util.Map;

@Data
public class CustomerOrder {
    private String customerId;
    private Map<String, Integer> items;
    private String deliveryAddress;
}