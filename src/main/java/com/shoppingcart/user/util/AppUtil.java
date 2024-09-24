package com.shoppingcart.user.util;

import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class AppUtil {

    public long minutesDiff(Date fecha1, Date fecha2) {
        long diffInMilliseconds = Math.abs(fecha1.getTime() - fecha2.getTime());
        return TimeUnit.MILLISECONDS.toMinutes(diffInMilliseconds);
    }
}
