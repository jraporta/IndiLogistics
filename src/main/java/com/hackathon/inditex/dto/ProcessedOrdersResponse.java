package com.hackathon.inditex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackathon.inditex.entities.OrderProcessingDetails;
import lombok.Data;

import java.util.List;

@Data
public class ProcessedOrdersResponse {

    @JsonProperty("processed-orders")
    private List<OrderProcessingDetails> processedOrders;

    public ProcessedOrdersResponse(List<OrderProcessingDetails> orderProcessingDetails) {
        this.processedOrders = orderProcessingDetails;
    }
}