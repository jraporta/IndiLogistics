package com.hackathon.inditex.dto;

import com.hackathon.inditex.entities.Coordinates;
import lombok.Data;

@Data
public class CreateCenterData {

    private String name;
    private String capacity;
    private String status;
    private int maxCapacity;
    private int currentLoad;
    private Coordinates coordinates;

}
