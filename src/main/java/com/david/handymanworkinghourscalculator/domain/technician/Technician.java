package com.david.handymanworkinghourscalculator.domain.technician;

public class Technician {

    private final TechnicianId technicianId;
    private final TechnicianName technicianName;
    private final TechnicianLastName technicianLastName;

    public Technician(TechnicianId technicianId, TechnicianName technicianName, TechnicianLastName technicianLastName) {
        this.technicianId = technicianId;
        this.technicianName = technicianName;
        this.technicianLastName = technicianLastName;
    }

    public TechnicianId getTechnicianId() {
        return technicianId;
    }

    public TechnicianName getTechnicianName() {
        return technicianName;
    }

    public TechnicianLastName getTechnicianLastName() {
        return technicianLastName;
    }
}
