package com.hackathon.inditex.services;

import com.hackathon.inditex.dto.CreateCenterData;
import com.hackathon.inditex.entities.Center;

import java.util.List;

public interface CenterService {
    void createCenter(CreateCenterData data);

    List<Center> getAllCenters();

    void deleteCenter(Long id);

    void updateCenter(Long id, CreateCenterData data);

    void increaseCurrentLoad(Center center);
}
