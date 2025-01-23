package com.hackathon.inditex.DTO;

import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.Entities.Order;
import lombok.Data;

@Data
public class CreateOrderResponse {

    private Long id;
    private Long customerId;
    private String size;
    private String assignedCenter;
    private Coordinates coordinates;
    private String status;
    private String message;

    public CreateOrderResponse(Order order, String message) {
        this.id = order.getId();
        this.customerId = order.getCustomerId();
        this.size = order.getSize();
        this.status = order.getStatus();
        this.assignedCenter = order.getAssignedCenter();
        this.coordinates = order.getCoordinates();
        this.message = message;
    }
}
