package com.david.handymanworkinghourscalculator.repository;

import com.david.handymanworkinghourscalculator.domain.technician.Technician;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianLastName;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianName;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TechnicianRepositoryTest {

    Technician technician;

    private TechnicianRepositoryImplementation technicianRepository;

    @BeforeAll
    public void init() {

        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("sql/technician-schema.sql")
                .addScript("sql/technician-test-data.sql")
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        technicianRepository = new TechnicianRepositoryImplementation(jdbcTemplate);

        TechnicianId technicianId = new TechnicianId("1036671650");
        TechnicianName technicianName = new TechnicianName("David Technician Test");
        TechnicianLastName technicianLastName = new TechnicianLastName("Vergara Technician Test");

        technician = new Technician(
                technicianId,
                technicianName,
                technicianLastName
        );
    }

    @Test
    @DisplayName("Method get all technicians check")
    public void getAllTechniciansCheck() {
        technicianRepository.addTechnician(technician);

        List<Technician> technicians = technicianRepository.getAllTechnicians();

        assertEquals(3, technicians.size());
    }

    @Test
    @DisplayName("Method get technician by technician id check")
    public void getTechnicianByIdCheck() {
        technicianRepository.addTechnician(technician);
        TechnicianId searchTechnicianId = technician.getTechnicianId();

        Technician foundedTechnician = technicianRepository.getTechnicianById(searchTechnicianId);

        assertEquals(searchTechnicianId.toString(), foundedTechnician.getTechnicianId().toString());
    }

    @Test
    @DisplayName("Method add technician check")
    public void addTechnicianCheck() {
        int actualSize = technicianRepository.getAllTechnicians().size();

        technicianRepository.addTechnician(technician);
        int finalSize = technicianRepository.getAllTechnicians().size();

        assertEquals(actualSize + 1, finalSize);
    }

    @Test
    @DisplayName("Method update technician check")
    public void updateTchnicianCheck() {

        technicianRepository.addTechnician(technician);

        TechnicianId technicianId = new TechnicianId("1036671650");
        TechnicianName technicianName = new TechnicianName("Updated Technician Name");
        TechnicianLastName technicianLastName = new TechnicianLastName("Updated Technician Last Name");

        Technician updateTechnician = new Technician(
                technicianId,
                technicianName,
                technicianLastName
        );

        technicianRepository.updateTechnician(updateTechnician);
        Technician updatedTechnician = technicianRepository
                .getTechnicianById(technicianId);

        assertEquals(
                updateTechnician.getTechnicianName().toString(),
                updatedTechnician.getTechnicianName().toString()
        );
        assertEquals(
                updateTechnician.getTechnicianLastName().toString(),
                updatedTechnician.getTechnicianLastName().toString()
        );
    }

    @Test
    @DisplayName("Method delete technician check")
    public void deleteTechnicianCheck() {
        technicianRepository.addTechnician(technician);
        TechnicianId deletedTechnicianId = technician.getTechnicianId();
        int actualSize = technicianRepository.getAllTechnicians().size();

        technicianRepository.deleteTechnician(deletedTechnicianId);
        int finalSize = technicianRepository.getAllTechnicians().size();

        assertEquals(actualSize - 1, finalSize);
    }

    @AfterEach
    public void end() {
        technicianRepository.deleteTechnician(technician.getTechnicianId());
    }
}
