package com.hackathon.inditex.Services;

import com.hackathon.inditex.DTO.CreateCenterData;
import com.hackathon.inditex.Entities.Capacity;
import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.Exceptions.InvalidCapacityException;
import com.hackathon.inditex.Exceptions.InvalidCenterCreationDataException;
import com.hackathon.inditex.Repositories.CenterRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CenterService {

    private CenterRepository centerRepository;

    public void createCenter(CreateCenterData data) throws InvalidCapacityException {
        if (data.getCurrentLoad() > data.getMaxCapacity()){
            throw new InvalidCenterCreationDataException("Current load cannot exceed max capacity.");
        }
        Center center = new Center();
        center.setName(data.getName());
        center.setCapacity(new Capacity(data.getCapacity()).getCapacity());
        center.setStatus(data.getStatus());
        center.setMaxCapacity(data.getMaxCapacity());
        center.setCurrentLoad(data.getCurrentLoad());
        center.setCoordinates(data.getCoordinates());
        try {
            centerRepository.save(center);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidCenterCreationDataException("There is already a logistics center in that position.");
        }
    }
}
