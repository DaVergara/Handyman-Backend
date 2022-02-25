package com.david.handymanworkinghourscalculator.service;

import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.exception.TechnicianNotFoundException;
import com.david.handymanworkinghourscalculator.domain.appointment.Appointment;
import com.david.handymanworkinghourscalculator.domain.HoursWorked;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Service
public class HoursWorkedService {

    private final AppointmentService appointmentService;
    private final TechnicianService technicianService;

    public HoursWorkedService(AppointmentService appointmentService, TechnicianService technicianService) {
        this.appointmentService = appointmentService;
        this.technicianService = technicianService;
    }

    public HoursWorked getHoursWorked(TechnicianId technicianId, WeekNumber weekNumber) {

        if (validateIfTechnicianExist(technicianId)) {
            HashMap<String, Integer> hoursWorkedMap = new HashMap<>();
            hoursWorkedMap.put("Horas Normales", 0);
            hoursWorkedMap.put("Horas Nocturnas", 0);
            hoursWorkedMap.put("Horas Dominicales", 0);
            hoursWorkedMap.put("Horas Normales Extra", 0);
            hoursWorkedMap.put("Horas Nocturnas Extra", 0);
            hoursWorkedMap.put("Horas Dominicales Extra", 0);

            List<Appointment> appointments
                    = appointmentService.getAppointmentsByTechnicianIdWeekNumber(technicianId, weekNumber);

            appointments.forEach(appointment -> {
                Duration duration = Duration.between(
                        appointment.getServiceStarted(), appointment.getServiceFinished()
                );
                int timeWorked = (int) duration.toMinutes();
                LocalDateTime startDateTimeFlag = appointment.getServiceStarted();
                LocalDateTime endDateTimeFlag = appointment.getServiceFinished();
                String overTime = "";
                while (timeWorked != 0) {

                    if (overTime(hoursWorkedMap) == 48 * 60
                            && overTime.equals("")) {
                        overTime = " Extra";
                    }

                    if (overTime(hoursWorkedMap) + timeWorked > 48 * 60
                            && overTime.equals("")) {
                        int x = 48 * 60 - overTime(hoursWorkedMap);
                        int y = timeWorked - x;
                        endDateTimeFlag = endDateTimeFlag.minusMinutes(y);
                    }

                    if (isSunday(startDateTimeFlag)
                            && isSunday(appointment.getServiceFinished().minusNanos(1))) {
                        addHoursWorked(
                                hoursWorkedMap,
                                "Horas Dominicales" + overTime,
                                startDateTimeFlag,
                                endDateTimeFlag
                        );
                        timeWorked -= (int) Duration
                                .between(startDateTimeFlag, endDateTimeFlag)
                                .toMinutes();
                        startDateTimeFlag = endDateTimeFlag;
                    } else if (sameTypeOfTime(startDateTimeFlag, endDateTimeFlag)) {
                        if (isNormalTime(startDateTimeFlag)
                                && isNormalTime(endDateTimeFlag)) {
                            addHoursWorked(
                                    hoursWorkedMap,
                                    "Horas Normales" + overTime,
                                    startDateTimeFlag,
                                    endDateTimeFlag
                            );
                        } else {
                            addHoursWorked(
                                    hoursWorkedMap,
                                    "Horas Nocturnas" + overTime,
                                    startDateTimeFlag,
                                    endDateTimeFlag
                            );
                        }
                        timeWorked -= (int) Duration
                                .between(startDateTimeFlag, endDateTimeFlag)
                                .toMinutes();
                        startDateTimeFlag = endDateTimeFlag;
                    } else {
                        if (startDateTimeFlag.isBefore(
                                startDateTimeFlag.toLocalDate().atTime(7, 0))
                        ) {
                            addHoursWorked(
                                    hoursWorkedMap,
                                    "Horas Nocturnas" + overTime,
                                    startDateTimeFlag,
                                    startDateTimeFlag.toLocalDate().atTime(7, 0)
                            );
                            timeWorked -= (int) Duration
                                    .between(startDateTimeFlag, startDateTimeFlag.toLocalDate().atTime(7, 0))
                                    .toMinutes();
                            startDateTimeFlag = startDateTimeFlag.toLocalDate().atTime(7, 0);
                        } else if (startDateTimeFlag.isBefore(
                                startDateTimeFlag.toLocalDate().atTime(20, 0))
                        ) {
                            addHoursWorked(
                                    hoursWorkedMap,
                                    "Horas Normales" + overTime,
                                    startDateTimeFlag,
                                    startDateTimeFlag.toLocalDate().atTime(20, 0)
                            );
                            timeWorked -= (int) Duration
                                    .between(startDateTimeFlag, startDateTimeFlag.toLocalDate().atTime(20, 0))
                                    .toMinutes();
                            startDateTimeFlag = startDateTimeFlag.toLocalDate().atTime(20, 0);
                        } else {
                            addHoursWorked(
                                    hoursWorkedMap,
                                    "Horas Nocturnas" + overTime,
                                    startDateTimeFlag,
                                    startDateTimeFlag.toLocalDate().atTime(0, 0).plusDays(1)
                            );
                            timeWorked -= (int) Duration
                                    .between(startDateTimeFlag, startDateTimeFlag.toLocalDate().atTime(0, 0)
                                            .plusDays(1))
                                    .toMinutes();
                            startDateTimeFlag = startDateTimeFlag.toLocalDate().atTime(0, 0).plusDays(1);
                        }
                    }

                    if (!endDateTimeFlag.equals(appointment.getServiceFinished())) {
                        endDateTimeFlag = appointment.getServiceFinished();
                    }
                }
            });
            return new HoursWorked(hoursWorkedMap);
        }

        throw new TechnicianNotFoundException("Technician not found");
    }

    private boolean validateIfTechnicianExist(TechnicianId technicianId) {
        try {
            return technicianService.getTechnicianById(technicianId) != null;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    private boolean sameTypeOfTime(LocalDateTime serviceStarted, LocalDateTime serviceFinished) {
        //Between 00:00 and 07:00
        if (serviceStarted.plusNanos(1).isAfter(serviceStarted.toLocalDate().atTime(0, 0))
                && serviceFinished.plusNanos(1).isAfter(serviceStarted.toLocalDate().atTime(0, 0))
                && serviceStarted.minusNanos(1).isBefore(serviceStarted.toLocalDate().atTime(7, 0))
                && serviceFinished.minusNanos(1).isBefore(serviceStarted.toLocalDate().atTime(7, 0))) {
            return true;
        }

        //Between 07:00 and 20:00
        if (serviceStarted.plusNanos(1).isAfter(serviceStarted.toLocalDate().atTime(7, 0))
                && serviceFinished.plusNanos(1).isAfter(serviceStarted.toLocalDate().atTime(7, 0))
                && serviceStarted.minusNanos(1).isBefore(serviceStarted.toLocalDate().atTime(20, 0))
                && serviceFinished.minusNanos(1).isBefore(serviceStarted.toLocalDate().atTime(20, 0))) {
            return true;
        }

        //Between 20:00 and 00:00
        return serviceStarted.plusNanos(1).isAfter(serviceStarted.toLocalDate().atTime(20, 0))
                && serviceFinished.plusNanos(1).isAfter(serviceStarted.toLocalDate().atTime(20, 0))
                && serviceStarted.minusNanos(1).isBefore(serviceStarted.toLocalDate().atTime(0, 0)
                .plusDays(1))
                && serviceFinished.minusNanos(1).isBefore(serviceStarted.toLocalDate().atTime(0, 0)
                .plusDays(1));
    }

    private int overTime(HashMap<String, Integer> hoursWorkedMap) {
        return hoursWorkedMap.get("Horas Normales")
                + hoursWorkedMap.get("Horas Nocturnas")
                + hoursWorkedMap.get("Horas Dominicales");
    }

    private boolean isNormalTime(LocalDateTime localDateTime) {
        return localDateTime.plusNanos(1).isAfter(localDateTime.toLocalDate().atTime(7, 0))
                && localDateTime.minusNanos(1).isBefore(localDateTime.toLocalDate().atTime(20, 0));
    }

    private boolean isSunday(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_WEEK_DATE;
        String[] strings = formatter.format(dateTime).split("-");
        return Integer.parseInt(strings[2]) == 7;
    }

    private void addHoursWorked(
            HashMap<String, Integer> hoursWorkedMap,
            String hoursWorkedMapKey,
            LocalDateTime timeStarted,
            LocalDateTime timeFinished
    ) {
        Integer hoursWorkedValue = hoursWorkedMap.get(hoursWorkedMapKey);
        Duration duration = Duration.between(timeStarted, timeFinished);
        int timeWorked = (int) duration.toMinutes();
        hoursWorkedMap.replace(hoursWorkedMapKey, hoursWorkedValue + timeWorked);
    }
}
