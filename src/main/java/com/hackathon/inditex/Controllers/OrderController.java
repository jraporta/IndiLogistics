package com.hackathon.inditex.Controllers;

import com.hackathon.inditex.DTO.CreateOrderData;
import com.hackathon.inditex.DTO.CreateOrderResponse;
import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.Services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("/api/orders")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderData data) {
        Order order = orderService.createOrder(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CreateOrderResponse(order, "Order created successfully in PENDING status."));
    }

    @GetMapping("/api/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

}
