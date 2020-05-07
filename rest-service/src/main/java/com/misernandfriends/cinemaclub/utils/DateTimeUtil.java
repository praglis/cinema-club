package com.misernandfriends.cinemaclub.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {
    public static Date getCurrentDate() {
        return Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTime();
    }

    public static Date minusDate(Date currentDate, int quantity, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(type, -1 * quantity);
        return calendar.getTime();
    }
}
