package com.david.handymanworkinghourscalculator.domain.appointment;

import com.david.handymanworkinghourscalculator.domain.service.ServiceId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Appointment {

    private final AppointmentId appointmentId;
    private final TechnicianId technicianId;
    private final ServiceId serviceId;
    private final LocalDateTime serviceStarted;
    private final LocalDateTime serviceFinished;
    private final WeekNumber weekNumber;

    public Appointment(
            AppointmentId appointmentId,
            TechnicianId technicianId,
            ServiceId serviceId,
            LocalDateTime serviceStarted,
            LocalDateTime serviceFinished,
            WeekNumber weekNumber
    ) {

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
}
