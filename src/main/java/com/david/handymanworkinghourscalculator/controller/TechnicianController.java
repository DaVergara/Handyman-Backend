package com.david.handymanworkinghourscalculator.controller;

import com.david.handymanworkinghourscalculator.exception.TechnicianNotFoundException;
import com.david.handymanworkinghourscalculator.model.Technician;
import com.david.handymanworkinghourscalculator.service.TechnicianService;
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
        } catch (TechnicianNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No seas puto", exception);
        }
    }

    @PostMapping
    public ResponseEntity<Technician> addTechnician(@RequestBody Technician technician) {
        Technician addedTechnician = service.addTechnician(technician);
        return new ResponseEntity<>(addedTechnician, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Technician> updateTechnician(@RequestBody Technician technician) {
        Technician updatedTechnician = service.updateTechnician(technician);
        return new ResponseEntity<>(updatedTechnician, HttpStatus.OK);
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
