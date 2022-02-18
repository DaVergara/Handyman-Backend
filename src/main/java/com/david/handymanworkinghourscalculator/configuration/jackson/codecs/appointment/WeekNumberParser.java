package com.david.handymanworkinghourscalculator.configuration.jackson.codecs.appointment;

import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.appointment.WeekNumber;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class WeekNumberParser {

    public static class Serializer extends JsonSerializer<WeekNumber> {

        @Override
        public void serialize(WeekNumber value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeNumber(value.asInteger());
        }
    }

    public static class Deserializer extends JsonDeserializer<WeekNumber> {

        @Override
        public WeekNumber deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            return new WeekNumber(p.getValueAsInt());
        }
    }

}
