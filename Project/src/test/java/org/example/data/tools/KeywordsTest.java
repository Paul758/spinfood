package org.example.data.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class KeywordsTest {


    @Test
    public void testKeywordCorrectness(){
        CSVReader.readValues("src/main/java/org/example/artifacts/teilnehmerliste.csv");
        Map<String, Integer> keywordMap = CSVReader.keyWordMap;

        Assertions.assertTrue(keywordMap.containsKey(Keywords.id));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.name));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.foodPreference));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.age));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.sex));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.kitchen));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.kitchenStory));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.kitchenLongitude));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.kitchenLatitude));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.idPartner));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.namePartner));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.agePartner));
        Assertions.assertTrue(keywordMap.containsKey(Keywords.sexPartner));

    }

}