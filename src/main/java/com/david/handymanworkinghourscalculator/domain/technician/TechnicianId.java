package com.david.handymanworkinghourscalculator.domain.technician;

import java.util.Objects;
import java.util.regex.Pattern;

public class TechnicianId {

    private static final Pattern pattern = Pattern.compile("^[0-9]{8,11}$");

    private final String value;

    public TechnicianId(String value) {

        Objects.requireNonNull(value, "Technician id can not be null.");

        String trimmedValue = value.trim();

        if (trimmedValue.length() == 0) {
            throw new IllegalArgumentException("Technician id can not be empty.");
        }

        boolean isValid = pattern.matcher(trimmedValue).matches();

        if (!isValid) {
            throw new IllegalArgumentException("Invalid technician id.");
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
