package com.david.handymanworkinghourscalculator.configuration.jackson;

import com.david.handymanworkinghourscalculator.configuration.jackson.codecs.appointment.AppointmentIdParser;
import com.david.handymanworkinghourscalculator.configuration.jackson.codecs.appointment.WeekNumberParser;
import com.david.handymanworkinghourscalculator.configuration.jackson.codecs.service.ServiceIdParser;
import com.david.handymanworkinghourscalculator.configuration.jackson.codecs.technician.TechnicianIdParser;
import com.david.handymanworkinghourscalculator.configuration.jackson.codecs.technician.TechnicianLastNameParser;
import com.david.handymanworkinghourscalculator.configuration.jackson.codecs.technician.TechnicianNameParser;
import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.david.handymanworkinghourscalculator.domain.service.ServiceId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianLastName;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianName;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class InternalModule extends SimpleModule {

    private static final String NAME = "InternalModule";

    public InternalModule() {

        super(NAME, Version.unknownVersion());

        //Technicians

        addSerializer(TechnicianId.class, new TechnicianIdParser.Serializer());
        addDeserializer(TechnicianId.class, new TechnicianIdParser.Deserializer());

        addSerializer(TechnicianName.class, new TechnicianNameParser.Serializer());
        addDeserializer(TechnicianName.class, new TechnicianNameParser.Deserializer());

        addSerializer(TechnicianLastName.class, new TechnicianLastNameParser.Serializer());
        addDeserializer(TechnicianLastName.class, new TechnicianLastNameParser.Deserializer());

        //Appointment

        addSerializer(AppointmentId.class, new AppointmentIdParser.Serializer());
        addDeserializer(AppointmentId.class, new AppointmentIdParser.Deserializer());

        addSerializer(WeekNumber.class, new WeekNumberParser.Serializer());
        addDeserializer(WeekNumber.class, new WeekNumberParser.Deserializer());

        //Service
        addSerializer(ServiceId.class, new ServiceIdParser.Serializer());
        addDeserializer(ServiceId.class, new ServiceIdParser.Deserializer());

    }

}
