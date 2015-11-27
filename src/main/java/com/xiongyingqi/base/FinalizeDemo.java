package com.xiongyingqi.base;

/**
 * @author <a href="http://xiongyingqi.com">qi</a>
 * @version 2015-10-20 15:17
 */
public class FinalizeDemo {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize...");
        super.finalize();
    }
    public static void main(String[] args) throws Throwable {
        FinalizeDemo demo = new FinalizeDemo();
        System.out.println(demo);
        demo = null;

        for (int i = 0; i < 10000000; i++) {
            new FinalizeDemo();
        }
    }
}
