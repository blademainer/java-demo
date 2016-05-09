package com.xiongyingqi.decimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xiongyingqi
 * @version 2016-05-09 21:08
 */
public class BigDecimalDemo {
    public static double divide(double a, double b) {
        BigDecimal numerator = new BigDecimal(a); // 分子
        BigDecimal denominator = new BigDecimal(b); // 分母

        double v = numerator.divide(denominator, 4, RoundingMode.HALF_UP).doubleValue();
        return v;
    }

    public static void main(String[] args) {
        double divide = divide(10, 3);
        System.out.println(divide);
        double divide2 = divide(20, 3);
        System.out.println(divide2);
    }
}
