package com.david.handymanworkinghourscalculator.repository;

import com.david.handymanworkinghourscalculator.model.Technician;

import java.util.List;

public interface TechnicianRepository {

    List<Technician> getAllTechnicians();

    Technician getTechnicianById(String technicianId);

    void addTechnician(Technician technician);

    void updateTechnician(Technician technician);

    void deleteTechnician(String technicianId);

}
