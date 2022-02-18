package com.david.handymanworkinghourscalculator.domain.technician;

import java.util.Objects;
import java.util.regex.Pattern;

public class TechnicianName {

    private static final Pattern pattern = Pattern.compile("^[a-zA-z \u00f1\u00d1]+$");

    private final String value;

    public TechnicianName(String value) {

        Objects.requireNonNull(value, "Technician name can not be null.");

        String trimmedValue = value.trim();

        if (trimmedValue.length() == 0) {
            throw new IllegalArgumentException("Technician name can not be empty.");
        }

        boolean isValid = pattern.matcher(trimmedValue).matches();

        if (!isValid) {
            throw new IllegalArgumentException("Invalid technician name.");
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
