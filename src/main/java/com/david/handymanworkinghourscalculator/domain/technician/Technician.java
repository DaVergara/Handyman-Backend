package com.david.handymanworkinghourscalculator.domain.technician;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Technician {

    private final TechnicianId technicianId;
    private final TechnicianName technicianName;
    private final TechnicianLastName technicianLastName;

}
