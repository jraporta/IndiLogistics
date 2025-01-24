package com.hackathon.inditex.services;

import com.hackathon.inditex.dto.CreateOrderData;
import com.hackathon.inditex.entities.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(CreateOrderData data);
    List<Order> getAllOrders();
    void assignCenter(Order order, String center);

}
