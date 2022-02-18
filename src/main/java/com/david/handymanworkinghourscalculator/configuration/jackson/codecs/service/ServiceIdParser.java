package com.david.handymanworkinghourscalculator.configuration.jackson.codecs.service;

import com.david.handymanworkinghourscalculator.domain.appointment.AppointmentId;
import com.david.handymanworkinghourscalculator.domain.service.ServiceId;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ServiceIdParser {

    public static class Serializer extends JsonSerializer<ServiceId> {

        @Override
        public void serialize(ServiceId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.toString());
        }
    }

    public static class Deserializer extends JsonDeserializer<ServiceId> {

        @Override
        public ServiceId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            return new ServiceId(p.getValueAsString());
        }
    }

}
