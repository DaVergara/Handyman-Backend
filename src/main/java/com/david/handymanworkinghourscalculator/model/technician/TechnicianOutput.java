package com.david.handymanworkinghourscalculator.model.technician;

import com.david.handymanworkinghourscalculator.domain.technician.Technician;

public class TechnicianOutput {

    private Technician technician;

    public TechnicianOutput() {
    }

    public TechnicianOutput(Technician technician) {
        this.technician = technician;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }
}
