package com.backend.boilerplate.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Slf4j
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        String localDate = parser.readValueAs(String.class);
        if (localDate.isBlank()) {
            return null;
        }
        return LocalDate.parse(localDate);
    }
}