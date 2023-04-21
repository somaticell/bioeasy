package cn.com.bioeasy.app.utils;

import android.text.format.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static String currentTime() {
        return getDate("yyyy-MM-dd HH:mm:ss:SSS", currentTimeMillis());
    }

    public static Date getDateFormStr(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isSameDay(long time1, long time2) {
        Time time = new Time();
        time.set(time1);
        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;
        time.set(time2);
        return thenYear == time.year && thenMonth == time.month && thenMonthDay == time.monthDay;
    }

    public static boolean isAfterDays(long startTime, long endTime, int days) {
        Time time = new Time();
        time.set(getNextDay(startTime, days));
        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;
        time.set(endTime);
        return thenYear == time.year && thenMonth == time.month && thenMonthDay == time.monthDay;
    }

    public static long getNextDay(long startTime, int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime);
        calendar.add(5, index);
        return calendar.getTime().getTime();
    }

    public static long getNextDay(int index) {
        return getNextDay(currentTimeMillis(), index);
    }

    public static String getDate(String template, long date) {
        if (date > 0) {
            return new SimpleDateFormat(template, Locale.CHINA).format(new Date(date));
        }
        return "";
    }

    public static String transTime(long time) {
        if (time <= 0) {
            return "";
        }
        long btm = (System.currentTimeMillis() - time) / 1000;
        if (btm <= 60) {
            return "刚刚";
        }
        long btm2 = btm / 60;
        if (btm2 <= 60) {
            return btm2 + "分钟前";
        }
        long btm3 = btm2 / 60;
        if (btm3 <= 24) {
            return btm3 + "小时前";
        }
        long btm4 = btm3 / 24;
        if (btm4 <= 3) {
            return btm4 + "天前";
        }
        return new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss", Locale.CHINA).format(new Date(time));
    }

    public static long getNowTime() {
        return System.currentTimeMillis();
    }
}
