package org.example.data.factory;

import org.example.data.enums.Sex;
import org.example.data.tools.CSVReader;
import org.example.data.tools.Keywords;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonFactoryTest {

    static Map<String, Integer> keywordMap = new HashMap<>();
    @BeforeAll
    public static void Setup() {
        keywordMap.put(Keywords.id, 0);
        keywordMap.put(Keywords.name, 1);
        keywordMap.put(Keywords.age, 2);
        keywordMap.put(Keywords.sex, 3);

        keywordMap.put(Keywords.idPartner, 4);
        keywordMap.put(Keywords.namePartner, 5);
        keywordMap.put(Keywords.agePartner, 6);
        keywordMap.put(Keywords.sexPartner, 7);
    }

    @Test
    void createPerson() {
        String expectedID = "001";
        String expectedName = "name";
        String expectedAge = "21";
        String expectedSex = "male";
        List<String> values = Arrays.asList(expectedID, expectedName, expectedAge, expectedSex);

        Person expectedPerson = new Person("001", "name", 21, Sex.MALE);
        Person actualPerson = PersonFactory.createPerson(values, keywordMap);

        Assertions.assertEquals(expectedPerson, actualPerson);
    }

    @Test
    void createPartner() {
        String expectedID = "001";
        String expectedName = "name";
        String expectedAge = "21";
        String expectedSex = "male";
        List<String> values = Arrays.asList("", "", "", "", expectedID, expectedName, expectedAge, expectedSex);

        Person expectedPerson = new Person("001", "name", 21, Sex.MALE);
        Person actualPerson = PersonFactory.createPartner(values, keywordMap);

        Assertions.assertEquals(expectedPerson, actualPerson);
    }
}