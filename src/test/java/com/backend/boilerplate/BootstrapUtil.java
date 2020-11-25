package com.backend.boilerplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Slf4j
public final class BootstrapUtil {

    private BootstrapUtil() {
        throw new AssertionError();
    }

    public static <T> boolean deepEquals(@NotNull T obj1, @NotNull T obj2) {
        if (Objects.isNull(obj1) || Objects.isNull(obj2)) {
            return false;
        }
        if (!Objects.equals(obj1.getClass(), obj2.getClass())) {
            return false;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        String object1, object2;
        try {
            object1 = objectMapper.writeValueAsString(obj1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        try {
            object2 = objectMapper.writeValueAsString(obj2);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return Objects.equals(object1, object2);
    }
}
