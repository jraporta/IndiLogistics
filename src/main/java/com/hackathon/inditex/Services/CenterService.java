package com.hackathon.inditex.Services;

import com.hackathon.inditex.DTO.CreateCenterData;
import com.hackathon.inditex.Entities.Capacity;
import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Exceptions.CenterNotExistsException;
import com.hackathon.inditex.Exceptions.InvalidCenterCreationDataException;
import com.hackathon.inditex.Repositories.CenterRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CenterService {

    private CenterRepository centerRepository;

    public void createCenter(CreateCenterData data) {
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

    public List<Center> getAllCenters() {
        return centerRepository.findAll();
    }

    public void deleteCenter(Long id) {
        InterruptIfNotExists(id);
        centerRepository.deleteById(id);
    }

    private void InterruptIfNotExists(Long id) {
        if (!centerRepository.existsById(id)) {
            throw new CenterNotExistsException("Center not found.");
        }
    }
}
