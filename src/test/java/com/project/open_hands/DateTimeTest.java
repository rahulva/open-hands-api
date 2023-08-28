package com.project.open_hands;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class DateTimeTest {
    @Test
    void testDateTimeFormat(){
        var str = "2023-08-28 19:37:27.242569";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]");

        var ptn = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSSSSS][.SS][.S]", Locale.US);  // Specify locale to determine human language and cultural norms used in translating
        LocalDateTime parse = LocalDateTime.parse(str, ptn);
        System.out.println("parse = " + parse);
    }
}
