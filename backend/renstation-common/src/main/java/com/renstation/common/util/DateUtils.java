package com.renstation.common.util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class DateUtils {
    public static String formatISO(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
    }
}
