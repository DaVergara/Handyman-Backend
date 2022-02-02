package com.david.handymanworkinghourscalculator.service;

import com.david.handymanworkinghourscalculator.exception.TechnicianNotFoundException;
import com.david.handymanworkinghourscalculator.model.Appointment;
import com.david.handymanworkinghourscalculator.model.HoursWorked;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.*;
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
            HashMap<String, Integer> hoursWorkedMap = new HashMap<>();
            hoursWorkedMap.put("Horas Normales", 0);
            hoursWorkedMap.put("Horas Nocturnas", 0);
            hoursWorkedMap.put("Horas Dominicales", 0);
            hoursWorkedMap.put("Horas Normales Extra", 0);
            hoursWorkedMap.put("Horas Nocturnas Extra", 0);
            hoursWorkedMap.put("Horas Dominicales Extra", 0);

            List<Appointment> appointments = getAppointmentsOfWeek(
                    appointmentService.getAppointmentsByTechnicianId(technicianId), weekNumber
            );

            appointments.forEach(appointment -> {
                Duration duration = Duration.between(
                        appointment.getServiceStarted(), appointment.getServiceFinished()
                );
                int hoursWorked = (int) duration.toHours();
                int i = 0;
                while (i != hoursWorked) {
                    if (isOvertime(hoursWorkedMap)) {
                        if (isSunday(appointment.getServiceStarted().plusHours(i))) {
                            addHoursWorked(
                                    hoursWorkedMap,
                                    "Horas Dominicales Extra"
                            );
                        } else if (isDaytimeHours(appointment.getServiceStarted().plusHours(i))) {
                            addHoursWorked(
                                    hoursWorkedMap,
                                    "Horas Normales Extra"
                            );
                        } else {
                            addHoursWorked(
                                    hoursWorkedMap,
                                    "Horas Nocturnas Extra"
                            );
                        }
                    } else {
                        if (isSunday(appointment.getServiceStarted().plusHours(i))) {
                            addHoursWorked(
                                    hoursWorkedMap,
                                    "Horas Dominicales"
                            );
                        } else if (isDaytimeHours(appointment.getServiceStarted().plusHours(i))) {
                            addHoursWorked(
                                    hoursWorkedMap,
                                    "Horas Normales"
                            );
                        } else {
                            addHoursWorked(
                                    hoursWorkedMap,
                                    "Horas Nocturnas"
                            );
                        }
                    }
                    i++;
                }
            });
            return new HoursWorked(hoursWorkedMap);
        }

        throw new TechnicianNotFoundException("Technician not found");
    }

    private boolean validateIfTechnicianExist(String technicianId) {
        try {
            return technicianService.getTechnicianById(technicianId) != null;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    private List<Appointment> getAppointmentsOfWeek(List<Appointment> appointments, int weekNumber) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_WEEK_DATE;

        appointments = appointments.stream().filter(appointment -> {
                    String[] serviceStarted = formatter.format(appointment.getServiceStarted()).split("-");
                    String[] serviceFinished = formatter.format(appointment.getServiceFinished()).split("-");
                    return Integer.parseInt(serviceStarted[1].replace("W", "")) == weekNumber
                            || Integer.parseInt(serviceFinished[1].replace("W", "")) == weekNumber;
                }).map(appointment -> {
                    String[] serviceStarted = formatter.format(appointment.getServiceStarted()).split("-");
                    if (Integer.parseInt(serviceStarted[1].replace("W", "")) != weekNumber) {
                        appointment.setServiceStarted(
                                (String.valueOf(weekNumber).length() == 1) ?
                                        LocalDate.parse("2022-W0" + weekNumber + "-1", formatter).atTime(LocalTime.MIN) :
                                        LocalDate.parse("2022-W" + weekNumber + "-1", formatter).atTime(LocalTime.MIN)
                        );
                    }
                    String[] serviceFinished = formatter.format(appointment.getServiceFinished()).split("-");
                    if (Integer.parseInt(serviceFinished[1].replace("W", "")) != weekNumber) {
                        appointment.setServiceFinished(
                                (String.valueOf(weekNumber + 1).length() == 1) ?
                                        LocalDate.parse("2022-W0" + (weekNumber + 1) + "-1", formatter).atTime(LocalTime.MIN) :
                                        LocalDate.parse("2022-W" + (weekNumber + 1) + "-1", formatter).atTime(LocalTime.MIN)
                        );
                    }
                    return appointment;
                })
                .collect(Collectors.toList());

        return appointments;
    }

    private boolean isOvertime(HashMap<String, Integer> hoursWorkedMap) {
        int weekTotalHours = hoursWorkedMap.get("Horas Normales") +
                hoursWorkedMap.get("Horas Nocturnas") +
                hoursWorkedMap.get("Horas Dominicales");
        return weekTotalHours == 48;
    }

    private boolean isSunday(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_WEEK_DATE;
        String[] strings = formatter.format(dateTime).split("-");
        return Integer.parseInt(strings[2]) == 7;
    }

    private boolean isDaytimeHours(LocalDateTime dateTime) {
        return dateTime.toLocalTime().isAfter(LocalTime.of(6, 59, 59))
                && dateTime.toLocalTime().isBefore(LocalTime.of(20, 0, 0));
    }

    private void addHoursWorked(
            HashMap<String, Integer> hoursWorkedMap,
            String hoursWorkedMapKey
    ) {
        Integer hoursWorkedValue = hoursWorkedMap.get(hoursWorkedMapKey);
        hoursWorkedMap.replace(hoursWorkedMapKey, hoursWorkedValue + 1);
    }
}
