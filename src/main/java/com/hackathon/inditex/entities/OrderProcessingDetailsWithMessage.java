package com.hackathon.inditex.entities;

import lombok.Data;

@Data
public class OrderProcessingDetailsWithMessage extends OrderProcessingDetails{

    private String message;

}
