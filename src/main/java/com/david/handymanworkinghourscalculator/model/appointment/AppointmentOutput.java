package com.david.handymanworkinghourscalculator.model.appointment;

import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentOutput {

    private Appointment appointment;

}
