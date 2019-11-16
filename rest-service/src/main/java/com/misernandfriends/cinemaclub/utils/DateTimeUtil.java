package com.misernandfriends.cinemaclub.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {

    public static Date getCurrentDate() {
        return Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTime();
    }

}
