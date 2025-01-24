package com.hackathon.inditex.entities;

import com.hackathon.inditex.exceptions.InvalidCenterCreationDataException;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "centers", uniqueConstraints = @UniqueConstraint(columnNames = {"latitude", "longitude"}))
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String capacity;

    private String status;

    private Integer currentLoad;

    private Integer maxCapacity;

    @Embedded
    private Coordinates coordinates;

    public void setCurrentLoad(Integer currentLoad) {
        if (currentLoad > this.getMaxCapacity()) {
            throw new InvalidCenterCreationDataException("Current load cannot exceed max capacity.");
        }
        this.currentLoad = currentLoad;
    }
}
