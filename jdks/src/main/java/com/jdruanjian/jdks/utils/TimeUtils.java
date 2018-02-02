package com.jdruanjian.jdks.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    /**
     * 转换成时间显示格式
     */
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat TIME_FORMAT2 = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    /**
     * 转换成时间显示格式
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat(
            "yyyy.MM.dd");

    public static String TimeToDisplay(long time) {
        if (time > 0) {
            return TIME_FORMAT.format(time * 1000L);
        } else {
            return null;
        }
    }

    public static String TimeToDisplay2(long time) {
        if (time > 0) {
            return TIME_FORMAT2.format(time * 1000L);
        } else {
            return null;
        }
    }

    public static String TimeToUpload(long time) {
        if (time > 0) {
            return TIME_FORMAT2.format(time);
        } else {
            return null;
        }
    }

    public static String DateToDisplay(long time) {
        if (time > 0) {
            return DATE_FORMAT.format(time * 1000L);
        } else {
            return null;
        }
    }

    public static String DateToDisplay2(long time) {
        if (time > 0) {
            return DATE_FORMAT2.format(time * 1000L);
        } else {
            return null;
        }
    }

    public static long StringToTimeStamp(String time) {
        if (!TextUtils.isEmpty(time)) {
            Date date;
            try {
                LogUtil.i(time);
                date = TIME_FORMAT.parse(time);
                return date.getTime();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return 0;

    }

    public static boolean canOrder(String time) {
        long time1 = System.currentTimeMillis();
        long time2 = StringToTimeStamp(time);
        return time2 - time1 > 20 * 60 * 1000;
    }

    public static boolean canOrder2(String time) {
        long time1 = System.currentTimeMillis();
        long time2 = Long.valueOf(time) * 1000;
        return time2 - time1 > 20 * 60 * 1000;
    }

    public static boolean canOrder3(String time1, String time2) {
        long time_1 = StringToTimeStamp(time1);
        long time_2 = Long.valueOf(time2) * 1000;
        return time_2 - time_1 > 20 * 60 * 1000;
    }

    public static boolean canRemind(String time, int min) {
        long time1 = System.currentTimeMillis();
        long time2 = StringToTimeStamp(time);
        if (time2 == 0) {
            return false;
        }
        return time2 - time1 > min * 60 * 1000;
    }

    public static long remindTime(String time, int min) {
        long time1 = StringToTimeStamp(time);
        return time1 - min * 60 * 1000;
    }
}
