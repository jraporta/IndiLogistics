package com.hackathon.inditex.DTO;

import com.hackathon.inditex.Entities.Coordinates;
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
