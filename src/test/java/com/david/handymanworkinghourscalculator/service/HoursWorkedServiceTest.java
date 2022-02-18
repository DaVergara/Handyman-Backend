package com.david.handymanworkinghourscalculator.service;

import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.service.ServiceId;
import com.david.handymanworkinghourscalculator.domain.technician.Technician;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianLastName;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianName;
import com.david.handymanworkinghourscalculator.exception.TechnicianNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class HoursWorkedServiceTest {

    List<Appointment> appointments = new ArrayList<>();

    AppointmentService mockAppointmentService = mock(AppointmentService.class);
    TechnicianService mockTechnicianService = mock(TechnicianService.class);
    HoursWorkedService service = new HoursWorkedService(mockAppointmentService, mockTechnicianService);

    @BeforeEach
    public void before() {
        try {
            List<String> strings = Files.readAllLines(Paths.get("./src/test/java/resources/appointments.txt"));
            strings.forEach(s -> {
                String[] strings1 = s.split(",");
                appointments.add(
                        new Appointment(
                                new AppointmentId(strings1[0].trim()),
                                new TechnicianId(strings1[1].trim()),
                                new ServiceId(strings1[2].trim()),
                                LocalDateTime.parse(strings1[3].trim()),
                                LocalDateTime.parse(strings1[4].trim()),
                                new WeekNumber(Integer.parseInt(strings1[5].trim()))
                                )
                );
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get hours worked method check")
    void getHoursWorkedCheck() {

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("Horas Normales", 1410);
        expected.put("Horas Nocturnas", 1470);
        expected.put("Horas Dominicales", 0);
        expected.put("Horas Normales Extra", 60);
        expected.put("Horas Nocturnas Extra", 50);
        expected.put("Horas Dominicales Extra", 1440);

        when(mockAppointmentService.getAppointmentsByTechnicianIdWeekNumber(appointments.get(0).getTechnicianId(), appointments.get(0).getWeekNumber()))
                .thenReturn(appointments);

        when(mockTechnicianService.getTechnicianById(appointments.get(0).getTechnicianId()))
                .thenReturn(new Technician(
                        appointments.get(0).getTechnicianId(),
                        new TechnicianName("David Alejandro"),
                        new TechnicianLastName("Vergara Arango")
                ));

        HashMap<String, Integer> result = service.getHoursWorked(
                appointments.get(0).getTechnicianId(),
                appointments.get(0).getWeekNumber()
        ).getHoursWorked();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Technician does not exist")
    void technicianDoesNotExist() {

        when(mockTechnicianService.getTechnicianById(appointments.get(0).getTechnicianId()))
                .thenReturn(null);

        Executable result = () -> service.getHoursWorked(
                appointments.get(0).getTechnicianId(),
                appointments.get(0).getWeekNumber()
        );

        assertThrows(TechnicianNotFoundException.class, result);
    }

}
