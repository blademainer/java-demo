package com.xiongyingqi.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiongyingqi on 16-6-8.
 */
public class CloneDemo {

    public static void main(String[] args) {
        Student one = new Student(1, "Tim");
        Student two = new Student(2, "Jack");

        Map<Integer, Student> map = new HashMap<Integer, Student>();
        map.put(one.getId(), one);
        map.put(two.getId(), two);

        Student tmp = map.get(1);
        Student student2 = tmp.clone();
        student2.setName("New");
        System.out.println(student2);
        System.out.println(map.get(1));
    }
}

class Student implements Cloneable {
    private Integer id;
    private String  name;

    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public Student clone() {
        Student student = new Student(id, name);
        return student;
    }
}
