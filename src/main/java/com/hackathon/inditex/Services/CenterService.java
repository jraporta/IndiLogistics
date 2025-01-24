package com.hackathon.inditex.Services;

import com.hackathon.inditex.DTO.CreateCenterData;
import com.hackathon.inditex.Entities.Capacity;
import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Exceptions.CenterNotExistsException;
import com.hackathon.inditex.Exceptions.InvalidCenterCreationDataException;
import com.hackathon.inditex.Exceptions.InvalidCenterUpdateDataException;
import com.hackathon.inditex.Repositories.CenterRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class CenterService {

    private CenterRepository centerRepository;

    public void createCenter(CreateCenterData data) {
        Center center = new Center();
        center.setName(data.getName());
        center.setCapacity(new Capacity(data.getCapacity()).getCapacity());
        center.setStatus(data.getStatus());
        center.setMaxCapacity(data.getMaxCapacity());
        center.setCurrentLoad(data.getCurrentLoad());
        center.setCoordinates(data.getCoordinates());
        saveCenter(center);
    }

    public List<Center> getAllCenters() {
        return centerRepository.findAll();
    }

    public void deleteCenter(Long id) {
        interruptIfNotExists(id);
        centerRepository.deleteById(id);
    }

    private void interruptIfNotExists(Long id) {
        if (!centerRepository.existsById(id)) {
            throw new CenterNotExistsException("Center not found.");
        }
    }

    public void updateCenter(Long id, CreateCenterData data) {
        interruptIfNotExists(id);
        Center center = centerRepository.getReferenceById(id);
        updateFirstCenterParameter(center, data);
        saveCenter(center);
    }

    private void updateFirstCenterParameter(Center center, CreateCenterData data) {
        if (updateParameter(data::getName, center::setName)) {
            return;
        }
        if (updateParameter(data::getCapacity, center::setCapacity)) {
            return;
        }
        if (updateParameter(data::getStatus, center::setStatus)) {
            return;
        }
        if (updateParameter(data::getCoordinates, center::setCoordinates)) {
            return;
        }
        if (data.getMaxCapacity() >0 && updateParameter(data::getMaxCapacity, center::setMaxCapacity)) {
            return;
        }
        if (updateParameter(data::getCurrentLoad, center::setCurrentLoad)) {
            return;
        }
        throw new InvalidCenterUpdateDataException("Invalid data provided: the center couldn't be updated");
    }

    private <T> boolean updateParameter(Supplier<T> getter, Consumer<T> setter) {
        if (getter.get() != null) {
            setter.accept(getter.get());
            return true;
        }
        return false;
    }

    private void saveCenter(Center center) {
        try {
            centerRepository.save(center);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidCenterCreationDataException("There is already a logistics center in that position.");
        }
    }

    public void increaseCurrentLoad(Center center) {
        center.setCurrentLoad(center.getCurrentLoad() + 1);
        centerRepository.save(center);
    }
}
