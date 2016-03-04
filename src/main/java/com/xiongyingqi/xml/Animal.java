package com.xiongyingqi.xml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

/**
 * @author xiongyingqi
 * @version 2015-12-16 09:55
 */
public class Animal<T> {
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public static void main(String[] args) throws IOException {
        Animal<String> animal = new Animal<>();
        animal.setT("呵呵<");
//        XmlFactory factory = new XmlFactory(new InputFactoryImpl(),
//                new CDataXmlOutputFactoryImpl());
        XmlMapper xmlMapper = new XmlMapper();
//        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String s = xmlMapper.writeValueAsString(animal);
        System.out.println(s);
        Object o = xmlMapper.readValue(s, getAnimal());
        System.out.println(o);
    }

    public static TypeReference getAnimal() {
        TypeReference<Animal<String>> animalTypeReference = new TypeReference<Animal<String>>() {
        };
        return animalTypeReference;
    }

}
