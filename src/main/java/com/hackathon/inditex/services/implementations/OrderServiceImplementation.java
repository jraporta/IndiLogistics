package com.hackathon.inditex.services.implementations;

import com.hackathon.inditex.dto.CreateOrderData;
import com.hackathon.inditex.entities.Order;
import com.hackathon.inditex.repositories.OrderRepository;
import com.hackathon.inditex.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImplementation implements OrderService {

    private OrderRepository orderRepository;

    @Override
    public Order createOrder(CreateOrderData data) {
        Order order = new Order();
        order.setCustomerId(data.getCustomerId());
        order.setSize(data.getSize());
        order.setStatus("PENDING");
        order.setCoordinates(data.getCoordinates());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void assignCenter(Order order, String center) {
        order.setAssignedCenter(center);
        order.setStatus("ASSIGNED");
        orderRepository.save(order);
    }
}
