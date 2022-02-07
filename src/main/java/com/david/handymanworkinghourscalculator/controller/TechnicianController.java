package com.david.handymanworkinghourscalculator.controller;

import com.david.handymanworkinghourscalculator.model.Technician;
import com.david.handymanworkinghourscalculator.service.TechnicianService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/technicians")
public class TechnicianController {

    private final TechnicianService service;

    public TechnicianController(TechnicianService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Technician>> getAllTechnicians() {
        List<Technician> technicians = service.getAllTechnicians();
        return new ResponseEntity<>(technicians, HttpStatus.OK);
    }

    @GetMapping("/{technicianId}")
    public ResponseEntity<Technician> getTechnicianById(@PathVariable("technicianId") String technicianId) {
        try {
            Technician technician = service.getTechnicianById(technicianId);
            return new ResponseEntity<>(technician, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Technician not found.", exception);
        }
    }

    @PostMapping
    public ResponseEntity<Technician> addTechnician(@RequestBody Technician technician) {
        try {
            Technician addedTechnician = service.addTechnician(technician);
            return new ResponseEntity<>(addedTechnician, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println("Hola");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Technician with id: " + technician.getTechnicianId() + " already exist.",
                    exception);
        }
    }

    @PutMapping
    public ResponseEntity<Technician> updateTechnician(@RequestBody Technician technician) {
        try {
            Technician updatedTechnician = service.updateTechnician(technician);
            return new ResponseEntity<>(updatedTechnician, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Technician with id: " + technician.getTechnicianId() + " not found.",
                    exception);
        }
    }

    @DeleteMapping("/{technicianId}")
    public ResponseEntity<?> deleteTechnician(@PathVariable("technicianId") String technicianId) {
        service.deleteTechnician(technicianId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/example")
    public ResponseEntity<HashMap<String, Integer>> example() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("David", 1);
        map.put("Alejandro", 2);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
