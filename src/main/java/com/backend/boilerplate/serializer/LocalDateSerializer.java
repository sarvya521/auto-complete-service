package com.backend.boilerplate.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Slf4j
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider serializers)
        throws IOException {
        try {
            if (value == null) {
                generator.writeNull();
            } else {
                generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
            }
        } catch (Exception ex) {
            log.error("Exception in LocalDateSerializer ", ex);
            throw new IOException(ex);
        }
    }
}