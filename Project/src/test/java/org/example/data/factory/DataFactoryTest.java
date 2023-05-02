package org.example.data.factory;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.enums.Sex;
import org.example.data.structures.EventParticipant;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.data.tools.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


class DataFactoryTest {

     static Map<String, Integer> keywordMap = new HashMap<>();
     static List<List<String>> data;

     static EventParticipant expectedSolo;
     static EventParticipant expectedPair;

     @BeforeAll
     static void setUp(){
          data = CSVReader.readValues("src/main/java/org/example/artifacts/testTeilnehmerliste.csv");
          keywordMap = CSVReader.createKeyWordMap(data.get(0));


          expectedSolo = new Solo(
                  new Person("004670cb-47f5-40a4-87d8-5276c18616ec","Person1", 21, Sex.MALE),
                  FoodPreference.VEGGIE,
                  new Kitchen(KitchenType.MAYBE, 3,8.673368271555807, 50.5941282715558)
          );

          expectedPair = new Pair(
                  new Person("01be5c1f-4aa1-458d-a530-b1c109ffbb55", "Person3",22, Sex.MALE),
                  new Person("117ee996-14d3-44e8-8bcb-eb2d29fddda5", "Personx1", 25, Sex.MALE),
                  FoodPreference.VEGAN,
                  new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
          );
     }

    @Test
    void createSinglePersonFromLine() {
        EventParticipant solo = DataFactory.createDataFromLine(data.get(1), keywordMap);
        Assertions.assertEquals(expectedSolo, solo);
    }

    @Test
    void createPairFromLine() {
        EventParticipant pair = DataFactory.createDataFromLine(data.get(2), keywordMap);
        Assertions.assertEquals(expectedPair, pair);
    }

}