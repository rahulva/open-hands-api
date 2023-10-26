package com.project.open_hands;

import java.time.format.DateTimeFormatter;

public final class Constants {
    private Constants() {
    }

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss[.SSSSSS]";
    public static final String UTC = "UTC";
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
}
