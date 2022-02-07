package com.david.handymanworkinghourscalculator.service;

import com.david.handymanworkinghourscalculator.model.Appointment;
import com.david.handymanworkinghourscalculator.model.Technician;
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

    public Appointment getAppointmentsByServiceId(String serviceId) {
        return repository.getAppointmentByServiceId(serviceId);
    }

    public Appointment addApoAppointment(Appointment appointment) {

        if (appointment.getServiceStarted().isAfter(appointment.getServiceFinished())
                || appointment.getServiceStarted().isEqual(appointment.getServiceFinished())) {
            throw new IllegalArgumentException("Service end date must be later than the service start date.");
        }

        repository.addAppointment(appointment);
        return appointment;
    }

    public Appointment updateAppointment(Appointment appointment) {
        repository.updateAppointment(appointment);
        return repository.getAppointmentByServiceId(appointment.getServiceId());
    }

    public void deleteAppointment(String serviceId) {
        repository.deleteAppointment(serviceId);
    }
}
