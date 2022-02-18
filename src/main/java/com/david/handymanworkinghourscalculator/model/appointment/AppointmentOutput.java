package com.david.handymanworkinghourscalculator.model.appointment;

import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;

public class AppointmentOutput {

    private Appointment appointment;

    public AppointmentOutput() {
    }

    public AppointmentOutput(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
