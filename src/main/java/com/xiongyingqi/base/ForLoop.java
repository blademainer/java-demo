package com.xiongyingqi.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qi on 16-8-25.
 */
public class ForLoop {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
