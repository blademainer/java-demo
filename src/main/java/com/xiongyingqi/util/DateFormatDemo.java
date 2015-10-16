package com.xiongyingqi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="http://xiongyingqi.com">qi</a>
 * @version 2015-10-16 14:44
 */
public class DateFormatDemo {
    private static DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        while (true) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    String format = FORMAT.format(new Date());
                    Date parse = FORMAT.parse(format);
                    System.out.println(format);
                    System.out.println(parse);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("cause unsafe thread! message: " + e.getMessage());
                    System.exit(0);
                }
            });
            thread.start();
        }
    }

}
