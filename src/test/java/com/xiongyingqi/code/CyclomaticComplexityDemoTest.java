package com.xiongyingqi.code;

import org.junit.Test;

import static org.junit.Assert.*;

public class CyclomaticComplexityDemoTest {

    @Test
    public void a() {
        CyclomaticComplexityDemo cyclomaticComplexityDemo = new CyclomaticComplexityDemo();
        cyclomaticComplexityDemo.a(10);
        cyclomaticComplexityDemo.a(20);
        cyclomaticComplexityDemo.a(30);
//        cyclomaticComplexityDemo.a(40);
//        cyclomaticComplexityDemo.a(50);
//        cyclomaticComplexityDemo.a(60);
//        cyclomaticComplexityDemo.a(70);
    }

    @Test
    public void b() {
        CyclomaticComplexityDemo2 cyclomaticComplexityDemo = new CyclomaticComplexityDemo2();
        cyclomaticComplexityDemo.b(10);
        cyclomaticComplexityDemo.b(20);
        cyclomaticComplexityDemo.b(30);
//        cyclomaticComplexityDemo.b(40);
//        cyclomaticComplexityDemo.b(50);
//        cyclomaticComplexityDemo.b(60);
//        cyclomaticComplexityDemo.b(70);
    }
}