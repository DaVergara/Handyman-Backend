package com.david.handymanworkinghourscalculator.controller;

import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.service.ServiceId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.exception.DatesOverlapException;
import com.david.handymanworkinghourscalculator.model.appointment.AppointmentInput;
import com.david.handymanworkinghourscalculator.model.appointment.AppointmentOutput;
import com.david.handymanworkinghourscalculator.service.AppointmentService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = service.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/{technicianId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByTechnicianId(
            @PathVariable("technicianId") TechnicianId technicianId
    ) {
        List<Appointment> appointments = service.getAppointmentsByTechnicianId(technicianId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/technician/{technicianId}/week/{weekNumber}")
    public ResponseEntity<List<Appointment>> getAppointmentsByTechnicianIdWeekNumber(
            @PathVariable("technicianId") TechnicianId technicianId,
            @PathVariable("weekNumber") int weekNumber
    ) {
        List<Appointment> appointments = service.getAppointmentsByTechnicianIdWeekNumber(technicianId, new WeekNumber(weekNumber));
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentByAppointmentId(@PathVariable("appointmentId") AppointmentId appointmentId) {
        try {
            Appointment appointment = service.getAppointmentsByAppointmentId(appointmentId);
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment not found.", exception);
        }
    }

    @PostMapping
    public ResponseEntity<List<Appointment>> addAppointment(@RequestBody AppointmentInput input) {
        try {
            AppointmentId appointmentId = AppointmentId.random();
            TechnicianId technicianId = new TechnicianId(input.getTechnicianId());
            ServiceId serviceId = new ServiceId(input.getServiceId());
            LocalDateTime serviceStarted = input.getServiceStarted();
            LocalDateTime serviceFinished = input.getServiceFinished();
            WeekNumber weekNumber = new WeekNumber(service.getWeek(input.getServiceStarted()));

            Appointment appointment = new Appointment(
                    appointmentId,
                    technicianId,
                    serviceId,
                    serviceStarted,
                    serviceFinished,
                    weekNumber
            );

            List<Appointment> addedAppointments = service.addAppointment(appointment);

            return new ResponseEntity<>(addedAppointments, HttpStatus.OK);
        } catch (IllegalArgumentException | DatesOverlapException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    exception.getMessage(), exception);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The technician with id: " + input.getTechnicianId() + " doesn't exist.",
                    exception);
        }
    }

    @PutMapping
    public ResponseEntity<AppointmentOutput> updateAppointment(@RequestBody AppointmentInput input) {
        try {
            AppointmentId appointmentId = new AppointmentId(input.getAppointmentId());
            TechnicianId technicianId = new TechnicianId(input.getTechnicianId());
            ServiceId serviceId = new ServiceId(input.getServiceId());
            LocalDateTime serviceStarted = input.getServiceStarted();
            LocalDateTime serviceFinished = input.getServiceFinished();
            WeekNumber weekNumber = new WeekNumber(service.getWeek(input.getServiceStarted()));

            Appointment appointment = new Appointment(
                    appointmentId,
                    technicianId,
                    serviceId,
                    serviceStarted,
                    serviceFinished,
                    weekNumber
            );

            Appointment updatedAppointment = service.updateAppointment(appointment);
            AppointmentOutput output = new AppointmentOutput(updatedAppointment);

            return new ResponseEntity<>(output, HttpStatus.OK);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable("appointmentId") AppointmentId appointmentId) {
        service.deleteAppointment(appointmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
