package com.xiongyingqi.algorithm;

/**
 * @author xiongyingqi
 * @version 2016-02-02 11:31
 */
public class Round {

    public static Long roundTo(long number, long round) {
        long rs = (number / round) * round;
        return rs;
    }

    public static void main(String[] args) throws InterruptedException {
        int roundTo = 5;
        while (true) {
            long time = System.currentTimeMillis();
            long unixTime = time / 1000;
            long round = roundTo(unixTime, roundTo);
            System.out.println("unixTime = " + unixTime);
            System.out.println(round);
            Thread.sleep(1000);
        }
    }
}
