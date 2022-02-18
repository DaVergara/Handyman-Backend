package com.david.handymanworkinghourscalculator.domain.technician;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TechnicianLastNameTest {

    @Test
    @DisplayName("Technician last name should be valid")
    void validValueCheck() {
        //arrange
        String value = "Vergara";

        //act
        TechnicianLastName technicianLastName = new TechnicianLastName(value);

        //assert
        assertEquals(value, technicianLastName.toString());
    }

    @Test
    @DisplayName("Technician last name null throws an error")
    void nullValueCheck() {

        String value = null;

        Executable executable = () -> new TechnicianLastName(value);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Empty technician id throws an error")
    void emptyValueCheck() {

        String value = "";

        Executable executable = () -> new TechnicianLastName(value);

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    @DisplayName("Invalid technician last name throws an error")
    void invalidValueCheck() {

        String value = "Ver@ra";

        Executable executable = () -> new TechnicianLastName(value);

        assertThrows(IllegalArgumentException.class, executable);
    }

}
