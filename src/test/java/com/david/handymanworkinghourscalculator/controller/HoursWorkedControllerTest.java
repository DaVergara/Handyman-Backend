package com.david.handymanworkinghourscalculator.controller;

import com.david.handymanworkinghourscalculator.domain.HoursWorked;
import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.service.AppointmentService;
import com.david.handymanworkinghourscalculator.service.HoursWorkedService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HoursWorkedControllerTest {

    HoursWorked hoursWorkedMock;

    HoursWorkedService mockService = mock(HoursWorkedService.class);
    HoursWorkedController hoursWorkedController = new HoursWorkedController(mockService);

    @BeforeEach
    public void init() {
        HashMap<String, Integer> hoursWorkedMap = new HashMap<>();
        hoursWorkedMap.put("Horas Normales", 0);
        hoursWorkedMap.put("Horas Nocturnas", 0);
        hoursWorkedMap.put("Horas Dominicales", 0);
        hoursWorkedMap.put("Horas Normales Extra", 0);
        hoursWorkedMap.put("Horas Nocturnas Extra", 0);
        hoursWorkedMap.put("Horas Dominicales Extra", 0);

        hoursWorkedMock = new HoursWorked(hoursWorkedMap);
    }

    @Test
    @DisplayName("Method get hours worked check")
    public void getHoursWorkedCheck() {
        TechnicianId technicianId = new TechnicianId("1036671649");
        WeekNumber weekNumber = new WeekNumber(8);

        when(mockService.getHoursWorked(technicianId, weekNumber)).thenReturn(hoursWorkedMock);
        HoursWorked hoursWorked = hoursWorkedController.getHoursWorked(technicianId, weekNumber).getBody();

        assertEquals(hoursWorkedMock, hoursWorked);
    }
}
