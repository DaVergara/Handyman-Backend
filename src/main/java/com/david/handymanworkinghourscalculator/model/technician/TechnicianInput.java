package com.david.handymanworkinghourscalculator.model.technician;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnicianInput {

    private String technicianId;
    private String technicianName;
    private String technicianLastName;

}
