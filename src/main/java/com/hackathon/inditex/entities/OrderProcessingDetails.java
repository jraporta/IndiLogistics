package com.hackathon.inditex.entities;

import lombok.Data;

@Data
public class OrderProcessingDetails {

    private Double distance;
    private Long orderId;
    private String assignedLogisticsCenter;
    private String status;

}
