package com.xiongyingqi.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author xiongyingqi
 * @version 2016-04-27 14:43
 */
public class JacksonConcurrentDemo {
    public static Collection<String> getCollection() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add("i" + i);
        }
        return strings;
    }

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 1000; i++) {
                    Collection<String> collection = getCollection();
                    try {
                        String s = mapper.writeValueAsString(collection);
                        System.out.println(s);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
