package com.david.handymanworkinghourscalculator.controller;

import com.david.handymanworkinghourscalculator.domain.technician.Technician;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianLastName;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianName;
import com.david.handymanworkinghourscalculator.model.technician.TechnicianInput;
import com.david.handymanworkinghourscalculator.model.technician.TechnicianOutput;
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
    public ResponseEntity<Technician> getTechnicianById(@PathVariable("technicianId") TechnicianId technicianId) {
        System.out.println(technicianId.toString());
        try {
            Technician technician = service.getTechnicianById(technicianId);
            return new ResponseEntity<>(technician, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Technician not found.", exception);
        }
    }

    @PostMapping
    public ResponseEntity<TechnicianOutput> addTechnician(@RequestBody TechnicianInput input) {
        try {
            TechnicianId technicianId = new TechnicianId(input.getTechnicianId());
            TechnicianName technicianName = new TechnicianName(input.getTechnicianName());
            TechnicianLastName technicianLastName = new TechnicianLastName(input.getTechnicianLastName());

            Technician technician = new Technician(
                    technicianId,
                    technicianName,
                    technicianLastName
            );

            Technician addedTechnician = service.addTechnician(technician);
            TechnicianOutput output = new TechnicianOutput(addedTechnician);

            return new ResponseEntity<>(output, HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    exception.getMessage(),
                    exception
            );
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Technician with id: " + input.getTechnicianId() + " already exist.",
                    exception
            );
        }
    }

    @PutMapping
    public ResponseEntity<TechnicianOutput> updateTechnician(@RequestBody TechnicianInput input) {
        try {
            TechnicianId technicianId = new TechnicianId(input.getTechnicianId());
            TechnicianName technicianName = new TechnicianName(input.getTechnicianName());
            TechnicianLastName technicianLastName = new TechnicianLastName(input.getTechnicianLastName());

            Technician technician = new Technician(
                    technicianId,
                    technicianName,
                    technicianLastName
            );

            Technician updatedTechnician = service.updateTechnician(technician);
            TechnicianOutput output = new TechnicianOutput(updatedTechnician);

            return new ResponseEntity<>(output, HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    exception.getMessage(),
                    exception
            );
        } catch (EmptyResultDataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Technician with id: " + input.getTechnicianId() + " not found.",
                    exception);
        }
    }

    @DeleteMapping("/{technicianId}")
    public ResponseEntity<?> deleteTechnician(@PathVariable("technicianId") TechnicianId technicianId) {
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
