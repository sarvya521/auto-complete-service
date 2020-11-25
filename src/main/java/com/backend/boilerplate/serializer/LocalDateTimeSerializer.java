package com.backend.boilerplate.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Slf4j
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        try {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeNumber(value.atOffset(ZoneOffset.UTC)
                    .toInstant()
                    .toEpochMilli());
            }
        } catch (Exception ex) {
            log.error("Exception in LocalDateSerializer ", ex);
            throw new IOException(ex);
        }
    }
}
