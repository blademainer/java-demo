package com.xiongyingqi.exception;

/**
 * @author qi
 * @version 2018/12/14
 */
public class ExceptionThread {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                String a = null;
                a.substring(1);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("I'm main thread, I'm ok!");
    }
}
