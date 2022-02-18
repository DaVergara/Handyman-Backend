package com.david.handymanworkinghourscalculator.domain.appointment;

import java.util.Objects;

public class WeekNumber {

    private final Integer value;

    public WeekNumber(Integer value) {
        Objects.requireNonNull(value, "Week number can not be null.");
        this.value = value;
    }

    public Integer asInteger() {
        return value;
    }
}
