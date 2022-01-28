package com.david.handymanworkinghourscalculator.service;

import com.david.handymanworkinghourscalculator.model.Appointment;
import com.david.handymanworkinghourscalculator.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public List<Appointment> getAllAppointments() {
        return repository.getAllAppointments();
    }

    public List<Appointment> getAppointmentsByTechnicianId(String technicianId) {
        return repository.getAppointmentsByTechnicianId(technicianId);
    }

    public Appointment addApoAppointment(Appointment appointment) {
        repository.addAppointment(appointment);
        return appointment;
    }
}
