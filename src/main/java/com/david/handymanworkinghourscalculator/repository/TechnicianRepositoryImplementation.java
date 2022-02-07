package com.david.handymanworkinghourscalculator.repository;

import com.david.handymanworkinghourscalculator.model.Technician;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TechnicianRepositoryImplementation implements TechnicianRepository {

    private final JdbcTemplate jdbcTemplate;

    public TechnicianRepositoryImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Technician> rowMapper = (resultSet, rowNum) -> {
        String technicianId = resultSet.getString("technician_id");
        String technicianName = resultSet.getString("technician_name");
        String technicianLastName = resultSet.getString("technician_lastname");

        return new Technician(
                technicianId,
                technicianName,
                technicianLastName
        );
    };

    @Override
    public List<Technician> getAllTechnicians() {
        String sqlQuery = "select * from tbl_technicians";
        return jdbcTemplate.query(sqlQuery, rowMapper);
    }

    @Override
    public Technician getTechnicianById(String technicianId) {
        String sqlQuery = "select * from tbl_technicians where technician_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, rowMapper, technicianId);
    }

    @Override

    public void addTechnician(Technician technician) {
        String sqlQuery =
                "insert into tbl_technicians(technician_id, technician_name, technician_lastname) " +
                        "values(?, ?, ?)";
        jdbcTemplate.update(sqlQuery, ps -> {
            ps.setString(1, technician.getTechnicianId());
            ps.setString(2, technician.getTechnicianName());
            ps.setString(3, technician.getTechnicianLastName());
        });
    }

    @Override
    public void updateTechnician(Technician technician) {
        String sqlQuery =
                "update tbl_technicians set technician_name = ?, technician_lastname = ? where technician_id = ?";
        jdbcTemplate.update(sqlQuery, ps -> {
            ps.setString(1, technician.getTechnicianName());
            ps.setString(2, technician.getTechnicianLastName());
            ps.setString(3, technician.getTechnicianId());
        });
    }

    @Override
    public void deleteTechnician(String technicianId) {
        String sqlQuery = "delete from tbl_technicians where technician_id = ?";
        jdbcTemplate.update(sqlQuery, technicianId);
    }
}
