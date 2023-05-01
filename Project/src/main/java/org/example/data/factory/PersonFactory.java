package org.example.data.factory;



import org.example.data.enums.Sex;
import org.example.data.tools.CSVReader;
import org.example.data.tools.Keywords;

import java.util.ArrayList;
import java.util.Collection;

/** Class to create the kitchen data objects
 *
 */
public class PersonFactory {

    public static Person createPerson(Collection<String> values) {
        ArrayList<String> data = new ArrayList<>(values);

        String id = data.get(CSVReader.keyWordMap.get(Keywords.id));
        String name = data.get(CSVReader.keyWordMap.get(Keywords.name));
        int age = Math.round(Float.parseFloat(data.get(CSVReader.keyWordMap.get(Keywords.age))));
        Sex sex = Sex.parseSex(data.get(CSVReader.keyWordMap.get(Keywords.sex)));

        return new Person(id, name, age, sex);
    }

    public static Person createPartner(Collection<String> values) {
        ArrayList<String> data = new ArrayList<>(values);

        String id = data.get(CSVReader.keyWordMap.get(Keywords.idPartner));
        String name = data.get(CSVReader.keyWordMap.get(Keywords.namePartner));
        int age = Math.round(Float.parseFloat(data.get(CSVReader.keyWordMap.get(Keywords.agePartner))));
        Sex sex = Sex.parseSex(data.get(CSVReader.keyWordMap.get(Keywords.sexPartner)));

        return new Person(id, name, age, sex);
    }
}
