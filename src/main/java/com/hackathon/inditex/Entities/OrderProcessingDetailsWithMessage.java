package com.hackathon.inditex.Entities;

import lombok.Data;

@Data
public class OrderProcessingDetailsWithMessage extends OrderProcessingDetails{

    private String message;

}
