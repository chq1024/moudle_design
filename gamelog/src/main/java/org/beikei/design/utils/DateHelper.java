package org.beikei.design.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateHelper {

    public static Long now() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
