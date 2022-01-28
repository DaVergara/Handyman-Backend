package com.david.handymanworkinghourscalculator.service;

import com.david.handymanworkinghourscalculator.exception.TechnicianNotFoundException;
import com.david.handymanworkinghourscalculator.model.Appointment;
import com.david.handymanworkinghourscalculator.model.HoursWorked;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoursWorkedService {

    private final AppointmentService appointmentService;
    private final TechnicianService technicianService;

    public HoursWorkedService(AppointmentService appointmentService, TechnicianService technicianService) {
        this.appointmentService = appointmentService;
        this.technicianService = technicianService;
    }

    public HoursWorked getHoursWorked(String technicianId, int weekNumber) {

        if (validateIfTechnicianExist(technicianId)) {

            HashMap<String, BigDecimal> hoursWorkedMap = new HashMap<>();

            hoursWorkedMap.put("Horas Normales", BigDecimal.ZERO);
            hoursWorkedMap.put("Horas Nocturnas", BigDecimal.ZERO);
            hoursWorkedMap.put("Horas Dominicales", BigDecimal.ZERO);
            hoursWorkedMap.put("Horas Normales Extra", BigDecimal.ZERO);
            hoursWorkedMap.put("Horas Nocturnas Extra", BigDecimal.ZERO);
            hoursWorkedMap.put("Horas Dominicales Extra", BigDecimal.ZERO);

            List<Appointment> appointments = appointmentService.getAppointmentsByTechnicianId(technicianId);
            appointments = getAppointmentsOfWeek(appointments, weekNumber);

            if (appointments.size() == 0) {
                HoursWorked hoursWorked = new HoursWorked(hoursWorkedMap);
                return  hoursWorked;
            } else {
                appointments.forEach(appointment -> {
                    if (isSunday(appointment)) {
                        BigDecimal hoursWorkedValue = addHoursWorked(
                                hoursWorkedMap, "Horas Dominicales", appointment
                        );
                        hoursWorkedMap.replace("Horas Dominicales", hoursWorkedValue);
                    } else {
                        if (isDaytimeHours(appointment)) {
                            System.out.println(appointment.getServiceStarted());
                            System.out.println("Dia");
                            BigDecimal hoursWorkedValue = addHoursWorked(
                                    hoursWorkedMap, "Horas Normales", appointment
                            );
                            hoursWorkedMap.replace("Horas Normales", hoursWorkedValue);
                        } else {
                            System.out.println(appointment.getServiceStarted());
                            System.out.println("Noche");
                            BigDecimal hoursWorkedValue = addHoursWorked(
                                    hoursWorkedMap, "Horas Nocturnas", appointment
                            );
                            hoursWorkedMap.replace("Horas Nocturnas", hoursWorkedValue);
                        }
                    }
                });
            }

            //System.out.println(time);

            HoursWorked hoursWorked = new HoursWorked(hoursWorkedMap);

            return hoursWorked;
        } else {
            throw new TechnicianNotFoundException("Technician Not Found");
        }
    }

    private boolean validateIfTechnicianExist(String technicianId) {
        try {
            return technicianService.getTechnicianById(technicianId) != null;
        } catch (TechnicianNotFoundException exception) {
            return false;
        }
    }

    private List<Appointment> getAppointmentsOfWeek(List<Appointment> appointments, int weekNumber) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_WEEK_DATE;

        appointments = appointments.stream().filter(appointment -> {
            String[] strings = formatter.format(appointment.getServiceStarted()).split("-");
            return Integer.parseInt(strings[1].replace("W", "")) == weekNumber;
        }).collect(Collectors.toList());
        return appointments;
    }

    private boolean isSunday(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_WEEK_DATE;
        String[] strings = formatter.format(appointment.getServiceStarted()).split("-");
        return Integer.parseInt(strings[2]) == 7;
    }

    private boolean isDaytimeHours(Appointment appointment) {
        System.out.println(appointment.getServiceStarted().toLocalTime());
        System.out.println(appointment.getServiceStarted().toLocalTime().isAfter(LocalTime.of(6, 59, 59))
                && appointment.getServiceStarted().toLocalTime().isBefore(LocalTime.of(20, 0, 1)));
        return appointment.getServiceStarted().toLocalTime().isAfter(LocalTime.of(6, 59, 59))
                && appointment.getServiceStarted().toLocalTime().isBefore(LocalTime.of(20, 0, 1));
    }

    private BigDecimal addHoursWorked(
            HashMap<String, BigDecimal> hoursWorkedMap,
            String hoursWorkedMapKey,
            Appointment appointment
    ) {
        BigDecimal hoursWorkedValue = hoursWorkedMap.get(hoursWorkedMapKey);
        Duration duration = Duration.between(
                appointment.getServiceStarted(), appointment.getServiceFinished()
        );
        BigDecimal appointmentHoursWorked = BigDecimal.valueOf(Math.abs(duration.toMinutes())/60d)
                .setScale(2, RoundingMode.UP);
        hoursWorkedValue = hoursWorkedValue.add(appointmentHoursWorked);
        return hoursWorkedValue;
    }
}
