package com.xiongyingqi.code;

public class CyclomaticComplexityDemo2 {
    public void b(int i) {
        if (i < 25) { // (-∞, 25)
            System.out.println("lt 25");
        } else if (i <= 50) {  // [25, 50]
            System.out.println("ge 25");
        } else if (i > 100) { // (50, 75]
            System.out.println("gt 100");
            while (i < 1000) {
                i++;
            }
        } else if (i <= 75) { // (50, 75]
            System.out.println("le 75");
        } else { // (75, 100]
            System.out.println("le 100");
        }
    }
}
