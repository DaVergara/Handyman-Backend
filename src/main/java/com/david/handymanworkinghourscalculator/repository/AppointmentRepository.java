package com.david.handymanworkinghourscalculator.repository;

import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;

import java.util.List;

public interface AppointmentRepository {

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByTechnicianId(TechnicianId technicianId);

    List<Appointment> getAppointmentsByTechnicianIdWeekNumber(TechnicianId technicianId, WeekNumber weekNumber);

    Appointment getAppointmentByAppointmentId(AppointmentId appointmentId);

    void addAppointment(Appointment appointment);

    void updateAppointment(Appointment appointment);

    void deleteAppointment(AppointmentId appointmentId);

}
