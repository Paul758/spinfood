package org.example.data.factory;



import org.example.data.enums.Sex;
import org.example.data.tools.CSVReader;
import org.example.data.tools.Keywords;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/** Class to create the person data objects
 *
 */
public class PersonFactory {

    /**
     * @param values Collection of values that are written in a single row in the .csv file
     * @param keyWordMap maps the column header keywords of the .csv file to integer indices. Is used to read the values
     * @return Returns a record that holds the values of a person
     */
    public static Person createPerson(Collection<String> values, Map<String, Integer> keyWordMap) {
        ArrayList<String> data = new ArrayList<>(values);

        String id = data.get(keyWordMap.get(Keywords.id));
        String name = data.get(keyWordMap.get(Keywords.name));
        int age = Math.round(Float.parseFloat(data.get(keyWordMap.get(Keywords.age))));
        Sex sex = Sex.parseSex(data.get(keyWordMap.get(Keywords.sex)));

        return new Person(id, name, age, sex);
    }

    /**
     * @param values Collection of values that are written in a single row in the .csv file
     * @param keyWordMap maps the column header keywords of the .csv file to integer indices. Is used to read the values
     * @return Returns a record that holds the values of a partner
     */
    public static Person createPartner(Collection<String> values, Map<String, Integer> keyWordMap) {
        ArrayList<String> data = new ArrayList<>(values);

        String id = data.get(keyWordMap.get(Keywords.idPartner));
        String name = data.get(keyWordMap.get(Keywords.namePartner));
        int age = Math.round(Float.parseFloat(data.get(keyWordMap.get(Keywords.agePartner))));
        Sex sex = Sex.parseSex(data.get(keyWordMap.get(Keywords.sexPartner)));

        return new Person(id, name, age, sex);
    }
}
