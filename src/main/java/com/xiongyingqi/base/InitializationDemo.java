package com.xiongyingqi.base;

/**
 * @author xiongyingqi
 * @since 17-2-16 下午3:15
 */
public class InitializationDemo {
    public static void main(String[] args) {
        System.out.println(new Child().id);
    }
}

class Parent{

    public Parent() {
        init();
    }

    void init(){}
}

class Child extends Parent {
    @Override
    void init() {
        System.out.println(id);
    }
    int id = 101;
}


