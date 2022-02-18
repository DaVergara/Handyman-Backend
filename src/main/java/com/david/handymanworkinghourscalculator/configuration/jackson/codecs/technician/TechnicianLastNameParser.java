package com.david.handymanworkinghourscalculator.configuration.jackson.codecs.technician;

import com.david.handymanworkinghourscalculator.domain.technician.TechnicianLastName;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TechnicianLastNameParser {

    public static class Serializer extends JsonSerializer<TechnicianLastName> {

        @Override
        public void serialize(TechnicianLastName value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.toString());
        }
    }

    public static class Deserializer extends JsonDeserializer<TechnicianLastName> {

        @Override
        public TechnicianLastName deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            return new TechnicianLastName(p.getValueAsString());
        }
    }

}
