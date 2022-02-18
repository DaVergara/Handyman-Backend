package com.david.handymanworkinghourscalculator.configuration.jackson.codecs.appointment;

import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.technician.TechnicianId;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AppointmentIdParser {

    public static class Serializer extends JsonSerializer<AppointmentId> {

        @Override
        public void serialize(AppointmentId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.toString());
        }
    }

    public static class Deserializer extends JsonDeserializer<AppointmentId> {

        @Override
        public AppointmentId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            return new AppointmentId(p.getValueAsString());
        }
    }

}
