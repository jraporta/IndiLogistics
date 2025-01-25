package com.hackathon.inditex.utils;

import com.hackathon.inditex.entities.Coordinates;

public interface DistanceCalculator {
    double calculateDistance(Coordinates c1, Coordinates c2);
}
