package com.david.handymanworkinghourscalculator.repository;

import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.service.ServiceId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppointmentRepositoryTest {

    Appointment appointment;

    private AppointmentRepositoryImplementation appointmentRepository;

    @BeforeAll
    public void init() {

        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("sql/appointment-schema.sql")
                .addScript("sql/appointment-test-data.sql")
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        appointmentRepository = new AppointmentRepositoryImplementation(jdbcTemplate);

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
        appointmentRepository.addAppointment(appointment);

        List<Appointment> appointments = appointmentRepository.getAllAppointments();

        assertEquals(3, appointments.size());
    }

    @Test
    @DisplayName("Method get appointment by appointment id check")
    public void getAppointmentByAppointmentIdCheck() {
        appointmentRepository.addAppointment(appointment);
        AppointmentId searchAppointmentId = appointment.getAppointmentId();

        Appointment foundedAppointment = appointmentRepository.getAppointmentByAppointmentId(searchAppointmentId);

        assertEquals(searchAppointmentId.toString(), foundedAppointment.getAppointmentId().toString());
    }

    @Test
    @DisplayName("Method get appointments by technician id and weekNumber check")
    public void getAppointmentsByTechnicianIdWeekNumberCheck() {
        appointmentRepository.addAppointment(appointment);
        TechnicianId searchTechnicianId = appointment.getTechnicianId();
        WeekNumber searchWeekNumber = appointment.getWeekNumber();

        List<Appointment> appointments = appointmentRepository
                .getAppointmentsByTechnicianIdWeekNumber(searchTechnicianId, searchWeekNumber);

        assertEquals(1, appointments.size());
    }

    @Test
    @DisplayName("Method add appointment check")
    public void addAppointmentCheck() {
        int actualSize = appointmentRepository.getAllAppointments().size();

        appointmentRepository.addAppointment(appointment);
        int finalSize = appointmentRepository.getAllAppointments().size();

        assertEquals(actualSize + 1, finalSize);
    }

    @Test
    @DisplayName("Method update appointment check")
    public void updateAppointmentCheck() {

        appointmentRepository.addAppointment(appointment);

        AppointmentId appointmentId = new AppointmentId("Testl1hV");
        TechnicianId technicianId = new TechnicianId("1036671649");
        ServiceId serviceId = new ServiceId("REPARACION");
        LocalDateTime serviceStarted = LocalDateTime.of(2022, Month.MARCH, 4, 12, 0);
        LocalDateTime serviceFinished = LocalDateTime.of(2022, Month.MARCH, 4, 15, 0);
        WeekNumber weekNumber = new WeekNumber(9);

        Appointment updateAppointment = new Appointment(
                appointmentId,
                technicianId,
                serviceId,
                serviceStarted,
                serviceFinished,
                weekNumber
        );

        appointmentRepository.updateAppointment(updateAppointment);
        Appointment updatedAppointment = appointmentRepository
                .getAppointmentByAppointmentId(appointmentId);

        assertEquals(updateAppointment.getServiceStarted(), updatedAppointment.getServiceStarted());
        assertEquals(updateAppointment.getServiceFinished(), updatedAppointment.getServiceFinished());
        assertEquals(updateAppointment.getWeekNumber().asInteger(), updatedAppointment.getWeekNumber().asInteger());
    }

    @Test
    @DisplayName("Method delete appointment check")
    public void deleteAppointmentCheck() {
        appointmentRepository.addAppointment(appointment);
        AppointmentId deletedAppointmentId = appointment.getAppointmentId();
        int actualSize = appointmentRepository.getAllAppointments().size();

        appointmentRepository.deleteAppointment(deletedAppointmentId);
        int finalSize = appointmentRepository.getAllAppointments().size();

        assertEquals(actualSize - 1, finalSize);
    }

    @AfterEach
    public void end() {
        appointmentRepository.deleteAppointment(appointment.getAppointmentId());
    }
}
