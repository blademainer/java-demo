package com.xiongyingqi.statement;

/**
 * @author xiongyingqi
 * @version 2016-04-18 16:42
 */
public class FinallyDemo {
    public static void justDoFinally(boolean justReturn) {
        try {
            System.out.println("try...");
            if(justReturn){
                return;
            }
            System.out.println("not returned...");
        } finally {
            System.out.println("finally...");
        }
    }
    public static void main(String[] args){
        justDoFinally(true);
        System.out.println("=============================");
        justDoFinally(false);
    }

}
