package com.david.handymanworkinghourscalculator.domain.service;

import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceIdTest {

    @Test
    @DisplayName("Service id should be valid")
    void validValueCheck() {
        //arrange
        String value = "REPARACION";

        //act
        ServiceId serviceId = new ServiceId(value);

        //assert
        assertEquals(value, serviceId.toString());
    }

    @Test
    @DisplayName("Appointment id null throws an error")
    void nullValueCheck() {
        String value = null;

        Executable executable = () -> new ServiceId(value);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Empty appointment id throws an error")
    void emptyValueCheck() {
        String value = "";

        Executable executable = () -> new ServiceId(value);

        assertThrows(IllegalArgumentException.class, executable);
    }
}
