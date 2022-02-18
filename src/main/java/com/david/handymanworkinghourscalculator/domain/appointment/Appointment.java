package com.david.handymanworkinghourscalculator.domain.appointment;

import com.david.handymanworkinghourscalculator.domain.service.ServiceId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;

import java.time.LocalDateTime;

public class Appointment {

    private final AppointmentId appointmentId;
    private final TechnicianId technicianId;
    private final ServiceId serviceId;
    private final LocalDateTime serviceStarted;
    private final LocalDateTime serviceFinished;
    private final WeekNumber weekNumber;

    public Appointment(AppointmentId appointmentId, TechnicianId technicianId, ServiceId serviceId, LocalDateTime serviceStarted, LocalDateTime serviceFinished, WeekNumber weekNumber) {

        if (serviceStarted.isAfter(serviceFinished)
                || serviceStarted.isEqual(serviceFinished)) {
            throw new IllegalArgumentException("Service end date must be later than the service start date.");
        }

        this.appointmentId = appointmentId;
        this.technicianId = technicianId;
        this.serviceId = serviceId;
        this.serviceStarted = serviceStarted;
        this.serviceFinished = serviceFinished;
        this.weekNumber = weekNumber;
    }

    public AppointmentId getAppointmentId() {
        return appointmentId;
    }

    public TechnicianId getTechnicianId() {
        return technicianId;
    }

    public ServiceId getServiceId() {
        return serviceId;
    }

    public LocalDateTime getServiceStarted() {
        return serviceStarted;
    }

    public LocalDateTime getServiceFinished() {
        return serviceFinished;
    }

    public WeekNumber getWeekNumber() {
        return weekNumber;
    }
}
