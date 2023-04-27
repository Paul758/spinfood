package org.example.data.factory;

import org.example.data.enums.Sex;

public record Person(String id, String name, int age, Sex sex) implements IData {

    @Override
    public String toString() {
        return "[" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                "] ";
    }
}
