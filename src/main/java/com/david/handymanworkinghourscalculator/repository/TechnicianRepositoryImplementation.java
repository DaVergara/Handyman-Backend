package com.david.handymanworkinghourscalculator.repository;

import com.david.handymanworkinghourscalculator.domain.technician.Technician;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianLastName;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianName;
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
        TechnicianId technicianId = new TechnicianId(resultSet.getString("technician_id"));
        TechnicianName technicianName = new TechnicianName(resultSet.getString("technician_name"));
        TechnicianLastName technicianLastName
                = new TechnicianLastName(resultSet.getString("technician_lastname"));

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
    public Technician getTechnicianById(TechnicianId technicianId) {
        String sqlQuery = "select * from tbl_technicians where technician_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, rowMapper, technicianId.toString());
    }

    @Override

    public void addTechnician(Technician technician) {
        String sqlQuery =
                "insert into tbl_technicians(technician_id, technician_name, technician_lastname) " +
                        "values(?, ?, ?)";
        jdbcTemplate.update(sqlQuery, ps -> {
            ps.setString(1, technician.getTechnicianId().toString());
            ps.setString(2, technician.getTechnicianName().toString());
            ps.setString(3, technician.getTechnicianLastName().toString());
        });
    }

    @Override
    public void updateTechnician(Technician technician) {
        String sqlQuery =
                "update tbl_technicians set technician_name = ?, technician_lastname = ? where technician_id = ?";
        jdbcTemplate.update(sqlQuery, ps -> {
            ps.setString(1, technician.getTechnicianName().toString());
            ps.setString(2, technician.getTechnicianLastName().toString());
            ps.setString(3, technician.getTechnicianId().toString());
        });
    }

    @Override
    public void deleteTechnician(TechnicianId technicianId) {
        String sqlQuery = "delete from tbl_technicians where technician_id = ?";
        jdbcTemplate.update(sqlQuery, technicianId.toString());
    }
}
