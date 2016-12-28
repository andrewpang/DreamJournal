package com.example.andrewpang.dreamjournal.Entries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

    public static String getMonthFromDate(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int month = cal.get(Calendar.MONTH);
        return new SimpleDateFormat("MMM").format(month);
    }

    public static String getDayNumberFromDate(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        return Integer.toString(day);
    }

    public static Long getTimeSince1970FromDate(Date date){
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getTimeInMillis();
    }

}
