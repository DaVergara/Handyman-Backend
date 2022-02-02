package com.david.handymanworkinghourscalculator.controller;

import com.david.handymanworkinghourscalculator.exception.TechnicianNotFoundException;
import com.david.handymanworkinghourscalculator.model.HoursWorked;
import com.david.handymanworkinghourscalculator.service.HoursWorkedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/hours_worked")
public class HoursWorkedController {

    private final HoursWorkedService service;

    public HoursWorkedController(HoursWorkedService service) {
        this.service = service;
    }

    @GetMapping("/technician/{technicianId}/week/{weekNumber}")
    ResponseEntity<HoursWorked> getHoursWorked(
            @PathVariable("technicianId") String technicianId, @PathVariable("weekNumber") int weekNumber
    ) {
        try {
            HoursWorked hoursWorked = service.getHoursWorked(technicianId, weekNumber);
            return new ResponseEntity<>(hoursWorked, HttpStatus.OK);
        } catch (TechnicianNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

}
