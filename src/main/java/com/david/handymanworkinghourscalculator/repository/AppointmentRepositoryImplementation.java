package com.david.handymanworkinghourscalculator.repository;

import com.david.handymanworkinghourscalculator.model.Appointment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class AppointmentRepositoryImplementation implements AppointmentRepository{

    private final JdbcTemplate jdbcTemplate;

    public AppointmentRepositoryImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Appointment> rowMapper = (resultSet, rowNum) -> {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String technicianId = resultSet.getString("technician_id");
        String serviceId = resultSet.getString("service_id");
        LocalDateTime serviceStarted = LocalDateTime.parse(resultSet.getString("service_started"), formatter);
        LocalDateTime serviceFinished = LocalDateTime.parse(resultSet.getString("service_finished"), formatter);

        return new Appointment(
                technicianId,
                serviceId,
                serviceStarted,
                serviceFinished
        );
    };

    @Override
    public List<Appointment> getAllAppointments() {
        String sqlQuery = "select * from tbl_appointments";
        return jdbcTemplate.query(sqlQuery, rowMapper);
    }

    @Override
    public List<Appointment> getAppointmentsByTechnicianId(String technicianId) {
        String sqlQuery = "select * from tbl_appointments where technician_id = ?";
        return jdbcTemplate.query(sqlQuery, rowMapper, technicianId);
    }

    @Override
    public void addAppointment(Appointment appointment) {
        String sqlQuery =
                "insert into tbl_appointments(technician_id, service_id, service_started, service_finished) " +
                        "values(?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, ps -> {
            ps.setString(1, appointment.getTechnicianId());
            ps.setString(2, appointment.getServiceId());
            ps.setObject(3, appointment.getServiceStarted());
            ps.setObject(4, appointment.getServiceFinished());
        });
    }
}
