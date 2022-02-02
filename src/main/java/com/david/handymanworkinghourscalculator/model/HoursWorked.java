package com.david.handymanworkinghourscalculator.model;

import java.math.BigDecimal;
import java.util.HashMap;

public class HoursWorked {

    private HashMap<String, Integer> hoursWorked;

    public HoursWorked(HashMap<String, Integer> hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public HashMap<String, Integer> getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(HashMap<String, Integer> hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
