package com.hackathon.inditex.Controllers;

import com.hackathon.inditex.DTO.ProcessedOrdersResponse;
import com.hackathon.inditex.Services.OrderProcessingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderProcessingController {

    private OrderProcessingService orderProcessingService;

    @PostMapping("/api/orders/order-assignations")
    public ResponseEntity<ProcessedOrdersResponse> processOrders(){
        ProcessedOrdersResponse response = new ProcessedOrdersResponse(orderProcessingService.processOrders());
        return ResponseEntity.ok(response);
    }

}
