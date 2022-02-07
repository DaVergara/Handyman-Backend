package com.david.handymanworkinghourscalculator.controller;

import com.david.handymanworkinghourscalculator.model.Appointment;
import com.david.handymanworkinghourscalculator.service.AppointmentService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
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

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<Appointment> getAppointmentByServiceId(@PathVariable("serviceId") String serviceId) {
        try {
            Appointment appointment = service.getAppointmentsByServiceId(serviceId);
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Technician not found.", exception);
        }
    }

    @PostMapping
    public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment) {
        try {
            String serviceId = "svc_" + RandomStringUtils.randomAlphanumeric(8);
            appointment.setServiceId(serviceId);
            Appointment addedAppointment = service.addApoAppointment(appointment);
            return new ResponseEntity<>(addedAppointment, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Service end date must be later than the service start date.", exception);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The technician with id: " + appointment.getTechnicianId() + " doesn't exist.",
                    exception);
        }
    }

    @PutMapping
    public ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment appointment) {
        Appointment updatedAppointment = service.updateAppointment(appointment);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @DeleteMapping("/service/{serviceId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable("serviceId") String serviceId) {
        service.deleteAppointment(serviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
