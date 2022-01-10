package com.fictiontimes.fictiontimesbackend.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date getLastMonthLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);

        return calendar.getTime();
    }
}
