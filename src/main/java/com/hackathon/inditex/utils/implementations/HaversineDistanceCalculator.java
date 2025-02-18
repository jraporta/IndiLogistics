package com.hackathon.inditex.utils.implementations;

import com.hackathon.inditex.entities.Coordinates;
import com.hackathon.inditex.utils.DistanceCalculator;


public class HaversineDistanceCalculator implements DistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371.0;

    @Override
    public double calculateDistance(Coordinates c1, Coordinates c2) {
        return haversine(c1.getLatitude(), c1.getLongitude(), c2.getLatitude(), c2.getLongitude());
    }

    private static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

}
