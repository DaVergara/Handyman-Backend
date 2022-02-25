package com.david.handymanworkinghourscalculator.controller;

import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.service.ServiceId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.model.appointment.AppointmentInput;
import com.david.handymanworkinghourscalculator.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AppointmentControllerTest {

    AppointmentService mockService = mock(AppointmentService.class);
    AppointmentController appointmentController = new AppointmentController(mockService);

    Appointment appointment;

    @BeforeEach
    void init() {

        AppointmentId appointmentId = new AppointmentId("Testl1hV");
        TechnicianId technicianId = new TechnicianId("1036671649");
        ServiceId serviceId = new ServiceId("REPARACION");
        LocalDateTime serviceStarted = LocalDateTime.of(2022, Month.FEBRUARY, 25, 12, 0);
        LocalDateTime serviceFinished = LocalDateTime.of(2022, Month.FEBRUARY, 25, 15, 0);
        WeekNumber weekNumber = new WeekNumber(8);

        appointment = new Appointment(
                appointmentId,
                technicianId,
                serviceId,
                serviceStarted,
                serviceFinished,
                weekNumber
        );
    }

    @Test
    @DisplayName("Method get all appointments check")
    public void getAllAppointmentsCheck() {

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        when(mockService.getAllAppointments()).thenReturn(appointments);

        assertEquals(HttpStatus.OK, appointmentController.getAllAppointments().getStatusCode());
        assertEquals(appointments, appointmentController.getAllAppointments().getBody());
    }

    @Test
    @DisplayName("Method get appointments by technician id and week number check")
    public void getAppointmentsByTechnicianIdWeekNumberCheck() {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        when(mockService.getAppointmentsByTechnicianIdWeekNumber(
                appointment.getTechnicianId(),
                appointment.getWeekNumber()
        )).thenReturn(appointments);

        assertEquals(HttpStatus.OK, appointmentController.getAppointmentsByTechnicianIdWeekNumber(
                appointment.getTechnicianId(),
                appointment.getWeekNumber()
        ).getStatusCode());
        assertEquals(appointments, appointmentController.getAppointmentsByTechnicianIdWeekNumber(
                appointment.getTechnicianId(),
                appointment.getWeekNumber()
        ).getBody());
    }

    @Test
    @DisplayName("Method add appointment check")
    public void addAppointmentCheck() {
        AppointmentInput appointmentInput = new AppointmentInput(
                appointment.getTechnicianId().toString(),
                appointment.getServiceId().toString(),
                appointment.getServiceStarted(),
                appointment.getServiceFinished()
        );
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        when(mockService.addAppointment(appointment)).thenReturn(appointments);

        assertEquals(HttpStatus.OK, appointmentController.addAppointment(appointmentInput).getStatusCode());
    }

    @Test
    @DisplayName("Method update appointment check")
    public void updateAppointment() {
        AppointmentInput appointmentInput = new AppointmentInput(
                appointment.getTechnicianId().toString(),
                appointment.getTechnicianId().toString(),
                appointment.getServiceId().toString(),
                appointment.getServiceStarted(),
                appointment.getServiceFinished()
        );

        when(mockService.updateAppointment(appointment)).thenReturn(appointment);

        assertEquals(HttpStatus.OK, appointmentController.updateAppointment(appointmentInput).getStatusCode());
    }

    @Test
    @DisplayName("Method delete appointment check")
    public void deleteAppointmentCheck() {

        assertEquals(HttpStatus.OK, appointmentController.deleteAppointment(
                appointment.getAppointmentId()
        ).getStatusCode());

    }

}
