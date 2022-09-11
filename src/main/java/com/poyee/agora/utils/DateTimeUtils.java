package com.poyee.agora.utils;

import java.time.LocalDateTime;

public class DateTimeUtils {
    public static String toString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.toString();
    }
}
