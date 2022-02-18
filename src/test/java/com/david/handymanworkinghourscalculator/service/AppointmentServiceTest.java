package com.david.handymanworkinghourscalculator.service;

import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.service.ServiceId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.exception.DatesOverlapException;
import com.david.handymanworkinghourscalculator.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppointmentServiceTest {

    AppointmentRepository mockRepository;
    AppointmentService service;

    Appointment appointment;

    @BeforeEach
    void init() {

        AppointmentId appointmentId = new AppointmentId("vS0JwX31");
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

        mockRepository = mock(AppointmentRepository.class);
        service = new AppointmentService(mockRepository);
    }

    @Test
    @DisplayName("Get all appointments method check")
    void getAllAppointmentsCheck() {

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        when(mockRepository.getAllAppointments()).thenReturn(appointments);

        assertEquals(appointments, service.getAllAppointments());
    }

    @Test
    @DisplayName("Get appointments by technician id method check")
    void getAppointmentsByTechnicianIdCheck() {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        when(mockRepository.getAppointmentsByTechnicianId(appointment.getTechnicianId()))
                .thenReturn(appointments);

        assertEquals(appointments, service.getAppointmentsByTechnicianId(appointment.getTechnicianId()));
    }

    @Test
    @DisplayName("Add appointment method check")
    void addAppointmentCheck() {
        List<Appointment> voidList = new ArrayList<>();

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        when(mockRepository.getAppointmentsByTechnicianId(appointment.getTechnicianId()))
                .thenReturn(voidList);

        assertEquals(appointments, service.addAppointment(appointment));
    }

    @Test
    @DisplayName("Add appointment method with different weeks check")
    void differentWeeks() {
        Appointment diffWeeks = new Appointment(
                new AppointmentId("vS0JwX31"),
                new TechnicianId("1036671649"),
                new ServiceId("REPARACION"),
                LocalDateTime.of(2022, Month.FEBRUARY, 27, 12, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 28, 12, 0),
                new WeekNumber(8)
        );

        List<Appointment> voidList = new ArrayList<>();

        Appointment started = new Appointment(
                new AppointmentId("vS0JwX31-1"),
                new TechnicianId("1036671649"),
                new ServiceId("REPARACION"),
                LocalDateTime.of(2022, Month.FEBRUARY, 27, 12, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 28, 0, 0),
                new WeekNumber(8)
        );

        Appointment ended = new Appointment(
                new AppointmentId("vS0JwX31-2"),
                new TechnicianId("1036671649"),
                new ServiceId("REPARACION"),
                LocalDateTime.of(2022, Month.FEBRUARY, 28, 0, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 28, 12, 0),
                new WeekNumber(9)
        );

        List<Appointment> expected = new ArrayList<>();
        expected.add(started);
        expected.add(ended);

        when(mockRepository.getAppointmentsByTechnicianId(appointment.getTechnicianId()))
                .thenReturn(voidList);

        List<Appointment> result = service.addAppointment(diffWeeks);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getAppointmentId().toString(), result.get(i).getAppointmentId().toString());
            assertEquals(expected.get(i).getTechnicianId().toString(), result.get(i).getTechnicianId().toString());
            assertEquals(expected.get(i).getServiceId().toString(), result.get(i).getServiceId().toString());
            assertEquals(expected.get(i).getServiceStarted().toString(), result.get(i).getServiceStarted().toString());
            assertEquals(expected.get(i).getServiceFinished().toString(), result.get(i).getServiceFinished().toString());
            assertEquals(expected.get(i).getWeekNumber().asInteger(), result.get(i).getWeekNumber().asInteger());
        }
    }

    @Test
    @DisplayName("Dates overlap check")
    void datesOverlapCheck() {

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        when(mockRepository.getAppointmentsByTechnicianId(appointment.getTechnicianId()))
                .thenReturn(appointments);


        Executable case1 = () -> new Appointment(
                new AppointmentId("vS0JwX3T"),
                appointment.getTechnicianId(),
                new ServiceId("REPARACION"),
                LocalDateTime.of(2022, Month.FEBRUARY, 25, 11, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 25, 13, 0),
                new WeekNumber(8)
        );

        Executable case2 = () -> service.addAppointment(
                new Appointment(
                        new AppointmentId("vS0JwX3T"),
                        appointment.getTechnicianId(),
                        new ServiceId("REPARACION"),
                        LocalDateTime.of(2022, Month.FEBRUARY, 25, 14, 0),
                        LocalDateTime.of(2022, Month.FEBRUARY, 25, 16, 0),
                        new WeekNumber(8)
                )
        );
        Executable case3 = () -> service.addAppointment(
                new Appointment(
                        new AppointmentId("vS0JwX3T"),
                        appointment.getTechnicianId(),
                        new ServiceId("REPARACION"),
                        LocalDateTime.of(2022, Month.FEBRUARY, 25, 11, 0),
                        LocalDateTime.of(2022, Month.FEBRUARY, 25, 16, 0),
                        new WeekNumber(8)
                )
        );

        assertThrows(DatesOverlapException.class, case2);
        assertThrows(DatesOverlapException.class, case2);
        assertThrows(DatesOverlapException.class, case3);
    }

    @Test
    @DisplayName("Update appointment method check")
    void updateAppointmentCheck() {

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        when(mockRepository.getAppointmentsByTechnicianId(appointment.getTechnicianId()))
                .thenReturn(appointments);

        when(mockRepository.getAppointmentByAppointmentId(appointment.getAppointmentId()))
                .thenReturn(appointment);

        assertEquals(appointment, service.updateAppointment(appointment));
    }
}
