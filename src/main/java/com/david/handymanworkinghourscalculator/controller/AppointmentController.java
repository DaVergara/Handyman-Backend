package com.david.handymanworkinghourscalculator.controller;

import com.david.handymanworkinghourscalculator.model.Appointment;
import com.david.handymanworkinghourscalculator.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
            @PathVariable("technicianId") String technicianId
    ) {
        List<Appointment> appointments = service.getAppointmentsByTechnicianId(technicianId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment addedAppointment = service.addApoAppointment(appointment);
            return new ResponseEntity<>(addedAppointment, HttpStatus.OK);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        }
    }

}
