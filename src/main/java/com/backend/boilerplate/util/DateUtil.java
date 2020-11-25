package com.backend.boilerplate.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

import static java.time.ZoneOffset.UTC;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
public final class DateUtil {
    private DateUtil() {
        throw new AssertionError();
    }

    public static Date convert(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(UTC).toInstant());
    }

    /**
     * @param localDateTime LocalDateTime
     * @return Date
     * @since 1.0.8
     */
    public static Date convert(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(UTC).toInstant());
    }

    /**
     * @param localDateTime LocalDateTime
     * @return Long
     * @since 1.0.8
     */
    public static Long toEpoch(LocalDateTime localDateTime) {
        return localDateTime.atOffset(UTC)
            .toInstant()
            .toEpochMilli();
    }

    /**
     * @param date Date
     * @return LocalDateTime
     * @since 1.0.8
     */
    public static LocalDateTime convert(Date date) {
        return date.toInstant().atZone(UTC).toLocalDateTime();
    }

    /**
     * @param epochMilli
     * @return Date
     * @since 1.0.10
     */
    public static Date convert(Long epochMilli) {
        Instant instant = Instant.ofEpochMilli(epochMilli);
        return convert(LocalDateTime.ofInstant(instant, ZoneOffset.UTC));
    }

    /**
     * @return current date in UTC zone
     * @since 1.0.32
     */
    public static Date getCurrentDate() {
        Instant instant = Instant.now();
        return convert(LocalDateTime.ofInstant(instant, ZoneOffset.UTC));
    }

    /**
     * General method to convert in ISO8601 format(specifically for FHIR patch update)
     *
     * @param dateTimeInEpoch Date for which conversion is requested
     * @return {@link String}
     */
    public static String convertToISOFormat(String dateTimeInEpoch) {
        TimeZone timeZone = TimeZone.getTimeZone(UTC);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(new Date(Long.parseLong(dateTimeInEpoch) * 1000));
    }
}
