package com.david.handymanworkinghourscalculator.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {

    private String technicianId;
    private String serviceId;
    private LocalDateTime serviceStarted;
    private LocalDateTime serviceFinished;

    public Appointment(String technicianId, String serviceId, LocalDateTime serviceStarted, LocalDateTime serviceFinished) {
        this.technicianId = technicianId;
        this.serviceId = serviceId;
        this.serviceStarted = serviceStarted;
        this.serviceFinished = serviceFinished;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDateTime getServiceStarted() {
        return serviceStarted;
    }

    public void setServiceStarted(LocalDateTime serviceStarted) {
        this.serviceStarted = serviceStarted;
    }

    public LocalDateTime getServiceFinished() {
        return serviceFinished;
    }

    public void setServiceFinished(LocalDateTime serviceFinished) {
        this.serviceFinished = serviceFinished;
    }
}
