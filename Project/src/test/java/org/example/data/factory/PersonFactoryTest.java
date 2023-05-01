package org.example.data.factory;

import org.example.data.enums.Sex;
import org.example.data.tools.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonFactoryTest {

    @Test
    void createPerson() {
        List<List<String>> valueLists = CSVReader.readValues("src/main/java/org/example/artifacts/testTeilnehmerListe.csv");
        List<String> firstEntry = valueLists.get(0);

        String expectedID = "004670cb-47f5-40a4-87d8-5276c18616ec";
        String expectedName = "Person1";
        int expectedAge = 21;
        Sex expectedSex = Sex.MALE;

        Person expectedPerson = new Person(expectedID, expectedName, expectedAge , expectedSex);
        Person actualPerson = PersonFactory.createPerson(firstEntry);

        Assertions.assertEquals(expectedPerson, actualPerson);
    }

    @Test
    void createPartner() {
        List<List<String>> valueLists = CSVReader.readValues("src/main/java/org/example/artifacts/testTeilnehmerListe.csv");
        List<String> firstEntry = valueLists.get(1);

        String expectedID = "117ee996-14d3-44e8-8bcb-eb2d29fddda5";
        String expectedName = "Personx1";
        int expectedAge = 25;
        Sex expectedSex = Sex.MALE;

        Person expectedPerson = new Person(expectedID, expectedName, expectedAge , expectedSex);
        Person actualPerson = PersonFactory.createPartner(firstEntry);

        Assertions.assertEquals(expectedPerson, actualPerson);
    }
}