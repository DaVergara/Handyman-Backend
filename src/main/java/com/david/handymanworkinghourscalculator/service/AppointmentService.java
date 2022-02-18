package com.david.handymanworkinghourscalculator.service;

import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.exception.DatesOverlapException;
import com.david.handymanworkinghourscalculator.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public List<Appointment> getAppointmentsByTechnicianId(TechnicianId technicianId) {
        return repository.getAppointmentsByTechnicianId(technicianId);
    }

    public List<Appointment> getAppointmentsByTechnicianIdWeekNumber(TechnicianId technicianId, WeekNumber weekNumber) {
        return repository.getAppointmentsByTechnicianIdWeekNumber(technicianId, weekNumber);
    }

    public Appointment getAppointmentsByAppointmentId(AppointmentId appointmentId) {
        return repository.getAppointmentByAppointmentId(appointmentId);
    }

    public List<Appointment> addAppointment(Appointment appointment) {

        if (datesOverlap(
                appointment.getAppointmentId(),
                appointment.getTechnicianId(),
                appointment.getServiceStarted(),
                appointment.getServiceFinished()))
        {
            throw new DatesOverlapException("The dates overlap with another appointment.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_WEEK_DATE;

        List<Appointment> appointments = new ArrayList<>();

        if (getWeek(appointment.getServiceStarted()) == getWeek(appointment.getServiceFinished())) {
            repository.addAppointment(appointment);
            appointments.add(appointment);
            return appointments;
        } else {
            Appointment appointment1 = new Appointment(
                    new AppointmentId(appointment.getAppointmentId().toString() + "-1"),
                    appointment.getTechnicianId(),
                    appointment.getServiceId(),
                    appointment.getServiceStarted(),
                    (String.valueOf(getWeek(appointment.getServiceFinished())).length() == 1) ?
                            LocalDate.parse(appointment.getServiceFinished().getYear() + "-W0" + getWeek(appointment.getServiceFinished()) + "-1", formatter).atTime(LocalTime.MIN) :
                            LocalDate.parse(appointment.getServiceFinished().getYear() + "-W" + getWeek(appointment.getServiceFinished()) + "-1", formatter).atTime(LocalTime.MIN),
                    new WeekNumber(getWeek(appointment.getServiceStarted()))
            );
            repository.addAppointment(appointment1);
            appointments.add(appointment1);
            Appointment appointment2 = new Appointment(
                    new AppointmentId(appointment.getAppointmentId().toString() + "-2"),
                    appointment.getTechnicianId(),
                    appointment.getServiceId(),
                    (String.valueOf(getWeek(appointment.getServiceStarted())).length() == 1) ?
                            LocalDate.parse( appointment.getServiceStarted().getYear() + "-W0" +  getWeek(appointment.getServiceFinished()) + "-1", formatter).atTime(LocalTime.MIN) :
                            LocalDate.parse( appointment.getServiceStarted().getYear() + "-W" +  getWeek(appointment.getServiceFinished()) + "-1", formatter).atTime(LocalTime.MIN),
                    appointment.getServiceFinished(),
                    new WeekNumber(getWeek(appointment.getServiceFinished()))
            );
            repository.addAppointment(appointment2);
            appointments.add(appointment2);
            return appointments;
        }
    }

    public Appointment updateAppointment(Appointment appointment) {

        if (datesOverlap(
                appointment.getAppointmentId(),
                appointment.getTechnicianId(),
                appointment.getServiceStarted(),
                appointment.getServiceFinished()))
        {
            throw new DatesOverlapException("The dates overlap with another appointment.");
        }

        repository.updateAppointment(appointment);
        return repository.getAppointmentByAppointmentId(appointment.getAppointmentId());
    }

    public void deleteAppointment(AppointmentId appointmentId) {
        repository.deleteAppointment(appointmentId);
    }

    private boolean datesOverlap(
            AppointmentId appointmentId,
            TechnicianId technicianId,
            LocalDateTime serviceStarted,
            LocalDateTime serviceFinished
    ) {
        boolean dateAssigned = false;
        List<Appointment> appointments = getAppointmentsByTechnicianId(technicianId);
        for (Appointment appointment: appointments) {
            if (
                    serviceStarted.isBefore(appointment.getServiceFinished())
                            && serviceFinished.isAfter(appointment.getServiceStarted())
                            && !appointmentId.toString().equals(appointment.getAppointmentId().toString())
            ) {
                dateAssigned = true;
                break;
            }
        }
        return dateAssigned;
    }

    public int getWeek(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_WEEK_DATE;
        String[] strings = formatter.format(dateTime).split("-");
        return Integer.parseInt(strings[1].replace("W", ""));
    }
}
