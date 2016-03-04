package com.xiongyingqi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xiongyingqi
 * @version 2016-01-26 15:30
 */
public class DateUtil {
    /**
     * 返回当天的前一天
     *
     * @return
     */
    private static String getYesterdayOfYear(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return format.format(calendar.getTime());
    }

    /**
     * 返回当天的前一天
     *
     * @return
     */
    private static String getYesterday(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DATE, -1);
        return format.format(calendar.getTime());
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date firstDayOfYear = format.parse("2016-01-01");
        String yesterday = getYesterday(firstDayOfYear);
        System.out.println("getYesterday: " + yesterday);
        String getYesterdayOfYear = getYesterdayOfYear(firstDayOfYear);
        System.out.println("getYesterdayOfYear: " + getYesterdayOfYear);
    }
}
