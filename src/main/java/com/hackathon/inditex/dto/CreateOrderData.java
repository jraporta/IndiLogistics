package com.hackathon.inditex.dto;

import com.hackathon.inditex.entities.Coordinates;
import lombok.Data;

@Data
public class CreateOrderData {

    private Long customerId;
    private String size;
    private Coordinates coordinates;

}
