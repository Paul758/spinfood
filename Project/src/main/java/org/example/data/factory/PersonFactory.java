package org.example.data.factory;



import org.example.data.enums.Sex;

import java.util.ArrayList;
import java.util.Collection;

public class PersonFactory {

    public static IData createDataObject(Collection<String> values) {
        ArrayList<String> data = new ArrayList<>(values);

        String id = data.get(0);
        String name = data.get(1);
        int age = Math.round(Float.parseFloat(data.get(2)));
        Sex sex = Sex.parseSex(data.get(3));

        return new Person(id, name, age, sex);
    }
}
