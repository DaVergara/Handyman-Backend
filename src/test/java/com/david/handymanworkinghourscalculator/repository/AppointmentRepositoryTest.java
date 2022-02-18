package com.david.handymanworkinghourscalculator.repository;

import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.ArgumentMatcher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.xmlunit.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class AppointmentRepositoryTest {

    AppointmentRepositoryImplementation appointmentRepository;

    JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);

    @Before
    public void before() {

        appointmentRepository = new AppointmentRepositoryImplementation(mockJdbcTemplate);
    }

    @Test
    @DisplayName("Get all appointments method check")
    public void getAllAppointmentsCheck() {

        List<Appointment> appointments = new ArrayList<>();

        String sqlQuery = "select * from tbl_appointments order by service_started ASC";

        when(mockJdbcTemplate.query(
                Mockito.anyString(),
                Mockito.any(Appointment[].class),
                ArgumentMatchers.<RowMapper<Mapper>>any()
        )).thenReturn(new ArrayList<>());

        assertEquals(appointments, appointmentRepository.getAllAppointments());
    }


}
