package com.backend.boilerplate.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.json.Json;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Slf4j
public final class JsonUtil {
    private JsonUtil() {
        throw new AssertionError();
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            log.error("error while converting json string {} to object", content, e);
            throw new RuntimeException(e);
        }
    }

    public static String writeValueAsString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error("error while parsing object {} to String", object, e);
            throw new RuntimeException(e);
        }
    }

    public static String format(JsonValue jsonValue) {
        StringWriter stringWriter = new StringWriter();
        prettyPrint(jsonValue, stringWriter);
        return stringWriter.toString();
    }

    public static void prettyPrint(JsonValue jsonValue, Writer writer) {
        Map<String, Object> config = Collections.singletonMap(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(config);
        try (JsonWriter jsonWriter = writerFactory.createWriter(writer)) {
            jsonWriter.write(jsonValue);
        }
    }
}
