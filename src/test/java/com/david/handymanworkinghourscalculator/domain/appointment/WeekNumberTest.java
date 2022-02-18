package com.david.handymanworkinghourscalculator.domain.appointment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeekNumberTest {

    @Test
    @DisplayName("Week number should be valid")
    void validValueCheck() {
        //arrange
        int value = 15;

        //act
        WeekNumber weekNumber = new WeekNumber(value);

        //assert
        assertEquals(value, weekNumber.asInteger());
    }

    @Test
    @DisplayName("Week number null throws an error")
    void nullValueCheck() {
        Integer value = null;

        Executable executable = () -> new WeekNumber(value);

        assertThrows(NullPointerException.class, executable);
    }

}
