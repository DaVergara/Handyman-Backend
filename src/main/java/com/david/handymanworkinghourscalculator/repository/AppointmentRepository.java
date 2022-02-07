package com.david.handymanworkinghourscalculator.repository;

import com.david.handymanworkinghourscalculator.model.Appointment;

import java.util.List;

public interface AppointmentRepository {

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByTechnicianId(String technicianId);

    Appointment getAppointmentByServiceId(String serviceId);

    void addAppointment(Appointment appointment);

    void updateAppointment(Appointment appointment);

    void deleteAppointment(String serviceIdId);

}
