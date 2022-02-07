package com.david.handymanworkinghourscalculator.service;

import com.david.handymanworkinghourscalculator.model.Technician;
import com.david.handymanworkinghourscalculator.repository.TechnicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianService {

    private final TechnicianRepository repository;

    public TechnicianService(TechnicianRepository repository) {
        this.repository = repository;
    }

    public List<Technician> getAllTechnicians() {
        return repository.getAllTechnicians();
    }

    public Technician getTechnicianById(String technicianId) {
            return repository.getTechnicianById(technicianId);
    }

    public Technician addTechnician(Technician technician) {

        repository.addTechnician(technician);
        return repository.getTechnicianById(technician.getTechnicianId());
    }

    public Technician updateTechnician(Technician technician) {
        repository.updateTechnician(technician);
        return repository.getTechnicianById(technician.getTechnicianId());
    }

    public void deleteTechnician(String technicianId) {
        repository.deleteTechnician(technicianId);
    }
}
