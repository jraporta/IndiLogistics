package com.hackathon.inditex.services;

import com.hackathon.inditex.entities.OrderProcessingDetails;

import java.util.List;

public interface OrderProcessingService {
    List<OrderProcessingDetails> processOrders();
}
