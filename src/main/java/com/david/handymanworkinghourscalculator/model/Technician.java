package com.david.handymanworkinghourscalculator.model;

public class Technician {

    private String technicianId;
    private String technicianName;
    private String technicianLastName;

    public Technician(String technicianId, String technicianName, String technicianLastName) {
        this.technicianId = technicianId;
        this.technicianName = technicianName;
        this.technicianLastName = technicianLastName;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getTechnicianLastName() {
        return technicianLastName;
    }

    public void setTechnicianLastName(String technicianLastName) {
        this.technicianLastName = technicianLastName;
    }
}
