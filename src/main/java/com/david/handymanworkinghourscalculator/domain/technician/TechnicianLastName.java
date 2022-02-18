package com.david.handymanworkinghourscalculator.domain.technician;

import java.util.Objects;
import java.util.regex.Pattern;

public class TechnicianLastName {

    private static final Pattern pattern = Pattern.compile("^[a-zA-z \u00f1\u00d1]+$");

    private final String value;

    public TechnicianLastName(String value) {

        Objects.requireNonNull(value, "Technician last name can not be null.");

        String trimmedValue = value.trim();

        if (trimmedValue.length() == 0) {
            throw new IllegalArgumentException("Technician last name can not be empty.");
        }

        boolean isValid = pattern.matcher(trimmedValue).matches();

        if (!isValid) {
            throw new IllegalArgumentException("Invalid technician last name.");
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
