package com.hackathon.inditex.config;

import com.hackathon.inditex.utils.DistanceCalculator;
import com.hackathon.inditex.utils.implementations.HaversineDistanceCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicConfiguration {

    @Bean
    DistanceCalculator getDistanceCalculator(){
        return new HaversineDistanceCalculator();
    }

}
