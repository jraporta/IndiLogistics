package com.hackathon.inditex.Services;

import com.hackathon.inditex.DTO.CreateOrderData;
import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.Repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    public Order createOrder(CreateOrderData data) {
        Order order = new Order();
        order.setCustomerId(data.getCustomerId());
        order.setSize(data.getSize());
        order.setStatus("PENDING");
        order.setCoordinates(data.getCoordinates());
        return orderRepository.save(order);
    }
}
