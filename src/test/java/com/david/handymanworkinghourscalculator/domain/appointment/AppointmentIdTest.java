package com.david.handymanworkinghourscalculator.domain.appointment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import static  org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppointmentIdTest {

    @Test
    @DisplayName("Appointment id should be valid")
    void validValueCheck() {
        //arrange
        String value = "vS0JwX31";

        //act
        AppointmentId appointmentId = new AppointmentId(value);

        //assert
        assertEquals(value, appointmentId.toString());
    }

    @Test
    @DisplayName("Appointment id should be valid")
    void randomValueCheck() {
        //arrange
        String value = AppointmentId.random().toString();

        //act
        AppointmentId appointmentId = new AppointmentId(value);

        //assert
        assertEquals(value, appointmentId.toString());
    }

    @Test
    @DisplayName("Appointment id null throws an error")
    void nullValueCheck() {
        String value = null;

        Executable executable = () -> new AppointmentId(value);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Empty appointment id throws an error")
    void emptyValueCheck() {
        String value = "";

        Executable executable = () -> new AppointmentId(value);

        assertThrows(IllegalArgumentException.class, executable);
    }
}
