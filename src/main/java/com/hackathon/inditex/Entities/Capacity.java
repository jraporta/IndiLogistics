package com.hackathon.inditex.Entities;

import com.hackathon.inditex.Exceptions.InvalidCapacityException;
import lombok.Getter;

@Getter
public class Capacity {

    private String capacity;

    public Capacity(String capacity) throws InvalidCapacityException {
        if (capacity.isEmpty() || capacity.length() > 3) {
            throw new InvalidCapacityException("Capacity can't be empty");
        }

        if ( ! capacity.replaceFirst(".*S.*", "")
                .replaceFirst(".*M.*", "")
                .replaceFirst(".*B.*", "")
                .isEmpty()){
            throw new InvalidCapacityException("Capacity must be S, M, B or a combination of those");
        }
        this.capacity = capacity;
    }

}
