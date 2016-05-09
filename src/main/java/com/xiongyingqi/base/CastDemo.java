package com.xiongyingqi.base;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * @author xiongyingqi
 * @version 2016-05-09 11:35
 */
public class CastDemo {
    public static int doubleStringToInt(String d) {
        double v = Double.parseDouble(d) * 100.00;
        //        System.out.println("double: " + v);
        int i = (int) v;
        return i;
    }

    public static int decimalStringToInt(String d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        BigDecimal multiply = bigDecimal.multiply(BigDecimal.valueOf(100.0));
        int i = multiply.intValue();
        //        double v = Double.parseDouble(d) * 100.00;
        //        System.out.println("double: " + v);
        //        int i = (int) v;
        return i;
    }

    /**
     * Converts a string representation of a number to a double. Need a faster
     * way to do this.
     *
     * @param stringVal the double as a String
     * @param colIndex  the 1-based index of the column to retrieve a double from.
     * @return the double value represented by the string in buf
     * @throws SQLException if an error occurs
     */
    protected double getDoubleInternal(String stringVal, int colIndex) throws SQLException {
        if ((stringVal == null)) {
            return 0;
        }

        double d = Double.parseDouble(stringVal);

        // Fix endpoint rounding precision loss in MySQL server
        if (d == 2.147483648E9) {
            // Fix Odd end-point rounding on MySQL
            d = 2.147483647E9;
        } else if (d == 1.0000000036275E-15) {
            // Fix odd end-point rounding on MySQL
            d = 1.0E-15;
        } else if (d == 9.999999869911E14) {
            d = 9.99999999999999E14;
        } else if (d == 1.4012984643248E-45) {
            d = 1.4E-45;
        } else if (d == 1.4013E-45) {
            d = 1.4E-45;
        } else if (d == 3.4028234663853E37) {
            d = 3.4028235E37;
        } else if (d == -2.14748E9) {
            d = -2.147483648E9;
        } else if (d == 3.40282E37) {
            d = 3.4028235E37;
        }

        return d;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            double d = 0.0;
            d = d + i * 0.01;
            int in = doubleStringToInt(d + "");
            //            System.out.println(in);
            //            Assert.equals(in, i);
            if (in != i) {
                System.err.println(i + " not equals(doubleStringToInt) " + in);
            }
        }
        for (int i = 0; i < 10000; i++) {
            double d = 0.0;
            d = d + i * 0.01;
            int in = decimalStringToInt(d + "");
            //            System.out.println(in);
            //            Assert.equals(in, i);
            if (in != i) {
                System.err.println(i + " not equals(decimalStringToInt) " + in);
            }
        }

        int in = doubleStringToInt("0.29");
        System.out.println(in);

        double d = 0.29;
        System.out.println(d);

    }
}
