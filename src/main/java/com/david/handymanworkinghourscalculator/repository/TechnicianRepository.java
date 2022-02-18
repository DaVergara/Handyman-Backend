package com.david.handymanworkinghourscalculator.repository;

import com.david.handymanworkinghourscalculator.domain.technician.Technician;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;

import java.util.List;

public interface TechnicianRepository {

    List<Technician> getAllTechnicians();

    Technician getTechnicianById(TechnicianId technicianId);

    void addTechnician(Technician technician);

    void updateTechnician(Technician technician);

    void deleteTechnician(TechnicianId technicianId);

}
