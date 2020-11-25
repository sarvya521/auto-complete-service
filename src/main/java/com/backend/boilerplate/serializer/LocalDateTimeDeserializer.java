package com.backend.boilerplate.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final Long utcTime = p.readValueAs(Long.class);
        if (utcTime == null) {
            return null;
        }
        final Instant instant = Instant.ofEpochMilli(utcTime);
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}