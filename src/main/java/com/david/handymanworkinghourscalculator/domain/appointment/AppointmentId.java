package com.david.handymanworkinghourscalculator.domain.appointment;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

public class AppointmentId {

    private final String value;

    public AppointmentId(String value) {

        Objects.requireNonNull(value, "Appointment id can not be null.");

        String trimmedValue = value.trim();

        if (trimmedValue.length() == 0) {
            throw new IllegalArgumentException("Appointment id can not be empty.");
        }

        this.value = value;
    }

    public static AppointmentId random() {
        return new AppointmentId(
                RandomStringUtils.randomAlphanumeric(8)
        );
    }

    @Override
    public String toString() {
        return value;
    }
}
