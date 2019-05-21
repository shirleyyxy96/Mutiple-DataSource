package com.augmentum.mediacloud.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date mixDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.getMinimum(Calendar.YEAR), calendar.getMinimum(Calendar.MONTH),
                calendar.getMinimum(Calendar.DATE), calendar.getMinimum(Calendar.HOUR_OF_DAY),
                calendar.getMinimum(Calendar.MINUTE), calendar.getMinimum(Calendar.SECOND));
        Date date = calendar.getTime();
        return date;
    }

    public static Date maxDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.getMaximum(Calendar.YEAR), calendar.getMaximum(Calendar.MONTH),
                calendar.getMaximum(Calendar.DATE), calendar.getMaximum(Calendar.HOUR_OF_DAY),
                calendar.getMaximum(Calendar.MINUTE), calendar.getMaximum(Calendar.SECOND));
        Date date = calendar.getTime();
        return date;
    }
}
