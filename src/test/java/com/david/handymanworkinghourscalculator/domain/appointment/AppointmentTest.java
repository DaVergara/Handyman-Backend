package com.david.handymanworkinghourscalculator.domain.appointment;

import com.david.handymanworkinghourscalculator.domain.service.ServiceId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppointmentTest {

    @Test
    @DisplayName("Appointment should be valid")
    void validAppointmentCheck() {
        //arrange
        AppointmentId appointmentId = new AppointmentId("vS0JwX31");
        TechnicianId technicianId = new TechnicianId("1036671649");
        ServiceId serviceId = new ServiceId("REPARACION");
        LocalDateTime serviceStarted = LocalDateTime.of(2022, Month.FEBRUARY, 25, 12, 0);
        LocalDateTime serviceFinished = LocalDateTime.of(2022, Month.FEBRUARY, 25, 15, 0);
        WeekNumber weekNumber = new WeekNumber(8);

        //act
        Appointment appointment = new Appointment(
                appointmentId,
                technicianId,
                serviceId,
                serviceStarted,
                serviceFinished,
                weekNumber
        );

        //assert
        assertEquals(appointmentId, appointment.getAppointmentId());
        assertEquals(technicianId, appointment.getTechnicianId());
        assertEquals(serviceId, appointment.getServiceId());
        assertEquals(serviceStarted, appointment.getServiceStarted());
        assertEquals(serviceFinished, appointment.getServiceFinished());
        assertEquals(weekNumber, appointment.getWeekNumber());
    }

    @Test
    @DisplayName("Appointment throws an error")
    void invalidAppointmentCheck1() {
        AppointmentId appointmentId = new AppointmentId("vS0JwX31");
        TechnicianId technicianId = new TechnicianId("1036671649");
        ServiceId serviceId = new ServiceId("REPARACION");
        LocalDateTime serviceStarted = LocalDateTime.of(2022, Month.FEBRUARY, 25, 12, 0);
        LocalDateTime serviceFinished = LocalDateTime.of(2022, Month.FEBRUARY, 25, 12, 0);
        WeekNumber weekNumber = new WeekNumber(8);

        Executable executable = () -> new Appointment(
                appointmentId,
                technicianId,
                serviceId,
                serviceStarted,
                serviceFinished,
                weekNumber
        );

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    @DisplayName("Appointment throws an error")
    void invalidAppointmentCheck2() {
        AppointmentId appointmentId = new AppointmentId("vS0JwX31");
        TechnicianId technicianId = new TechnicianId("1036671649");
        ServiceId serviceId = new ServiceId("REPARACION");
        LocalDateTime serviceStarted = LocalDateTime.of(2022, Month.FEBRUARY, 25, 15, 0);
        LocalDateTime serviceFinished = LocalDateTime.of(2022, Month.FEBRUARY, 25, 12, 0);
        WeekNumber weekNumber = new WeekNumber(8);

        Executable executable = () -> new Appointment(
                appointmentId,
                technicianId,
                serviceId,
                serviceStarted,
                serviceFinished,
                weekNumber
        );

        assertThrows(IllegalArgumentException.class, executable);
    }

}
