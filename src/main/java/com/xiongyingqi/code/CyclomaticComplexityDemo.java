package com.xiongyingqi.code;

public class CyclomaticComplexityDemo {
    public void a(int i) {
        if (i > 50) {
            if (i <= 75) {
                System.out.println("le 75");
            } else if (i <= 100) {
                System.out.println("le 100");
            } else {
                System.out.println("gt 100");
                while (i < 1000) {
                    i++;
                }
            }
        } else if (i >= 25) {
            System.out.println("ge 25");
        } else {
            System.out.println("lt 25");
        }
    }


}
