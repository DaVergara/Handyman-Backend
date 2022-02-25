package com.david.handymanworkinghourscalculator.repository;

import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.service.ServiceId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class AppointmentRepositoryImplementation implements AppointmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppointmentRepositoryImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Appointment> rowMapper = (resultSet, rowNum) -> {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        AppointmentId appointmentId = new AppointmentId(resultSet.getString("appointment_id"));
        TechnicianId technicianId = new TechnicianId(resultSet.getString("technician_id"));
        ServiceId serviceId = new ServiceId(resultSet.getString("service_id"));
        LocalDateTime serviceStarted = LocalDateTime.parse(resultSet.getString("service_started"), formatter);
        LocalDateTime serviceFinished = LocalDateTime.parse(resultSet.getString("service_finished"), formatter);
        WeekNumber weekNumber = new WeekNumber(resultSet.getInt("week_number"));

        return new Appointment(
                appointmentId,
                technicianId,
                serviceId,
                serviceStarted,
                serviceFinished,
                weekNumber
        );
    };

    @Override
    public List<Appointment> getAllAppointments() {
        String sqlQuery = "select * from tbl_appointments order by service_started ASC";
        return jdbcTemplate.query(sqlQuery, rowMapper);
    }

    @Override
    public List<Appointment> getAppointmentsByTechnicianId(TechnicianId technicianId) {
        String sqlQuery = "select * from tbl_appointments where technician_id = ? order by service_started ASC";
        return jdbcTemplate.query(sqlQuery, rowMapper, technicianId.toString());
    }

    @Override
    public List<Appointment> getAppointmentsByTechnicianIdWeekNumber(TechnicianId technicianId, WeekNumber weekNumber) {
        String sqlQuery
                = "select * from tbl_appointments where technician_id = ? and week_number = ? " +
                "order by service_started ASC";
        return jdbcTemplate.query(sqlQuery, rowMapper, technicianId.toString(), weekNumber.asInteger());
    }

    @Override
    public Appointment getAppointmentByAppointmentId(AppointmentId appointmentId) {
        String sqlQuery = "select * from tbl_appointments where appointment_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, rowMapper, appointmentId.toString());
    }

    @Override
    public void addAppointment(Appointment appointment) {
        String sqlQuery
                = "insert into tbl_appointments" +
                "(appointment_id, technician_id, service_id, service_started, service_finished, week_number) " +
                "values(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, ps -> {
            ps.setString(1, appointment.getAppointmentId().toString());
            ps.setString(2, appointment.getTechnicianId().toString());
            ps.setString(3, appointment.getServiceId().toString());
            ps.setObject(4, appointment.getServiceStarted());
            ps.setObject(5, appointment.getServiceFinished());
            ps.setInt(6, appointment.getWeekNumber().asInteger());
        });
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        String sqlQuery =
                "update tbl_appointments set service_started = ?, service_finished = ?, week_number = ? " +
                        "where appointment_id = ?";
        jdbcTemplate.update(sqlQuery, ps -> {
            ps.setObject(1, appointment.getServiceStarted());
            ps.setObject(2, appointment.getServiceFinished());
            ps.setInt(3, appointment.getWeekNumber().asInteger());
            ps.setString(4, appointment.getAppointmentId().toString());
        });
    }

    @Override
    public void deleteAppointment(AppointmentId appointmentId) {
        String sqlQuery = "delete from tbl_appointments where appointment_id = ?";
        jdbcTemplate.update(sqlQuery, appointmentId.toString());
    }
}
