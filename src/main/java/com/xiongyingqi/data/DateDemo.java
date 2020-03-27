package com.xiongyingqi.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDemo {
    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .format(new Date()));
        // RFC3339
        System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
                .format(new Date()));
    }
}
