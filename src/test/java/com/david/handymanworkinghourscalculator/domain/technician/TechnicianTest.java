package com.david.handymanworkinghourscalculator.domain.technician;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TechnicianTest {

    @Test
    @DisplayName("Technician should be valid")
    void validAppointmentCheck() {
        //arrange
        TechnicianId technicianId = new TechnicianId("1036671649");
        TechnicianName technicianName = new TechnicianName("David Alejandro");
        TechnicianLastName technicianLastName = new TechnicianLastName("Vergara Arango");

        //act
        Technician technician = new Technician(
                technicianId,
                technicianName,
                technicianLastName
        );

        //assert
        assertEquals(technicianId, technician.getTechnicianId());
        assertEquals(technicianName, technician.getTechnicianName());
        assertEquals(technicianLastName, technician.getTechnicianLastName());
    }

}
