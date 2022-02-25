package com.david.handymanworkinghourscalculator.controller;

import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.exception.TechnicianNotFoundException;
import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import com.david.handymanworkinghourscalculator.domain.HoursWorked;
import com.david.handymanworkinghourscalculator.service.AppointmentService;
import com.david.handymanworkinghourscalculator.service.HoursWorkedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/hours_worked")
public class HoursWorkedController {

    private final HoursWorkedService service;

    public HoursWorkedController(HoursWorkedService hoursWorkedService) {
        this.service = hoursWorkedService;
    }

    @GetMapping("/technician/{technicianId}/week/{weekNumber}")
    ResponseEntity<HoursWorked> getHoursWorked(
            @PathVariable("technicianId") TechnicianId technicianId, @PathVariable("weekNumber") WeekNumber weekNumber
    ) {
        try {
            HoursWorked hoursWorked = service.getHoursWorked(technicianId, weekNumber);
            return new ResponseEntity<>(hoursWorked, HttpStatus.OK);
        } catch (TechnicianNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

}
