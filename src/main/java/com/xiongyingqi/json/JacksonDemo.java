package com.xiongyingqi.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;

import java.io.IOException;
import java.util.*;

/**
 * @author xiongyingqi
 * @version 2016-03-29 11:23
 */
public class JacksonDemo {

    public static ObjectMapper getMapper() {
        return new ObjectMapper();
    }

    public String writeAsString(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }

    /**
     * 获取反序列化的集合类型JavaType
     *
     * @param clazz 元素类型
     * @return {@link JavaType}
     */
    public static JavaType getListType(Class<?> clazz) {
        CollectionType construct = CollectionType
                .construct(LinkedList.class, SimpleType.construct(clazz));
        return construct;
    }

    /**
     * 获取反序列化的map类型JavaType
     *
     * @param keyType   键类型
     * @param valueType 值类型
     * @return {@link JavaType}
     */
    public static JavaType getMapType(Class<?> keyType, Class<?> valueType) {
        MapType construct = MapType.construct(HashMap.class, SimpleType.construct(keyType),
                SimpleType.construct(valueType));
        return construct;
    }

    public static void main(String[] args) throws IOException {
        List<Entry> list = new ArrayList<Entry>();
        list.add(new Entry("one", "hello"));
        list.add(new Entry("two", "world"));
        list.add(new Entry("three", "!"));
        JacksonDemo jacksonDemo = new JacksonDemo();
        String listJson = jacksonDemo.writeAsString(list);
        System.out.println(listJson);
        JavaType listType = getListType(Entry.class);
        ObjectMapper mapper = getMapper();
        List<Entry> result = mapper.readValue(listJson, listType);
        System.out.println(result);
        System.out.println(result.getClass()); // LinkedList

        List list1 = mapper.readValue(listJson, List.class);
        for (Object a : list1) {
            System.out.println("a===" + a);
            System.out.println("a.class===" + a.getClass());
        }
        System.out.println("list1===========" + list1);
        System.out.println("list1 class===========" + list1.getClass());

        Map<String, String> map = new HashMap<String, String>();
        map.put("one", "hello");
        map.put("two", "world");
        String mapJson = jacksonDemo.writeAsString(map);
        System.out.println(mapJson);
        JavaType mapType = getMapType(String.class, String.class);
        Map<String, String> result2 = mapper.readValue(mapJson, mapType);
        System.out.println(result2);
        System.out.println(result2.getClass()); // HashMap
    }
}

class Entry {
    private String key;
    private String value;

    public Entry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Entry() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
