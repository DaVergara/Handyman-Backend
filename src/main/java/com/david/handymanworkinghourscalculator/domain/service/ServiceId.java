package com.david.handymanworkinghourscalculator.domain.service;

import java.util.Objects;

public class ServiceId {

    private final String value;

    public ServiceId(String value) {
        Objects.requireNonNull(value, "Service type id can not be null.");

        String trimmedValue = value.trim();

        if (trimmedValue.length() == 0) {
            throw new IllegalArgumentException("Appointment id can not be empty.");
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
