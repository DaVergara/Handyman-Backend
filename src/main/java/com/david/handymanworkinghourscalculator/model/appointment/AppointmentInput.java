package com.david.handymanworkinghourscalculator.model.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentInput {

    private String appointmentId;
    private String technicianId;
    private String serviceId;
    private LocalDateTime serviceStarted;
    private LocalDateTime serviceFinished;

    public AppointmentInput(
            String technicianId,
            String serviceId,
            LocalDateTime serviceStarted,
            LocalDateTime serviceFinished
    ) {
        this.technicianId = technicianId;
        this.serviceId = serviceId;
        this.serviceStarted = serviceStarted;
        this.serviceFinished = serviceFinished;
    }
}
