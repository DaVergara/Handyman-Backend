package com.david.handymanworkinghourscalculator.domain.technician;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TechnicianIdTest {

    @Test
    @DisplayName("Technician id should be valid")
    void validValueCheck() {
        //arrange
        String value = "1036671649";

        //act
        TechnicianId technicianId = new TechnicianId(value);

        //assert
        assertEquals(value, technicianId.toString());
    }

    @Test
    @DisplayName("Technician id null throws an error")
    void nullValueCheck() {

        String value = null;

        Executable executable = () -> new TechnicianId(value);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Empty technician id throws an error")
    void emptyValueCheck() {

        String value = "";

        Executable executable = () -> new TechnicianId(value);

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    @DisplayName("Invalid technician id throws an error")
    void invalidValueCheck() {

        String value = "David10366";

        Executable executable = () -> new TechnicianId(value);

        assertThrows(IllegalArgumentException.class, executable);
    }

}
