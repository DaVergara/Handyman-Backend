package com.david.handymanworkinghourscalculator.model;

import java.math.BigDecimal;
import java.util.HashMap;

public class HoursWorked {

    private HashMap<String, BigDecimal> hoursWorked;

    public HoursWorked(HashMap<String, BigDecimal> hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public HashMap<String, BigDecimal> getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(HashMap<String, BigDecimal> hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
