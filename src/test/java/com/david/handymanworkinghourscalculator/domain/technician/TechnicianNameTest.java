package com.david.handymanworkinghourscalculator.domain.technician;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TechnicianNameTest {

    @Test
    @DisplayName("Technician last name should be valid")
    void validValueCheck() {
        //arrange
        String value = "David";

        //act
        TechnicianName technicianName = new TechnicianName(value);

        //assert
        assertEquals(value, technicianName.toString());
    }

    @Test
    @DisplayName("Technician last name null throws an error")
    void nullValueCheck() {

        String value = null;

        Executable executable = () -> new TechnicianName(value);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Empty technician id throws an error")
    void emptyValueCheck() {

        String value = "";

        Executable executable = () -> new TechnicianName(value);

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    @DisplayName("Invalid technician name throws an error")
    void invalidValueCheck() {

        String value = "D@avid";

        Executable executable = () -> new TechnicianName(value);

        assertThrows(IllegalArgumentException.class, executable);
    }

}
