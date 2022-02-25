package com.david.handymanworkinghourscalculator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class HoursWorked {

    private HashMap<String, Integer> hoursWorked;

}
