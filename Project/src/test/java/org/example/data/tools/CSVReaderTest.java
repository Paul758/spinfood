package org.example.data.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {

    @Test
    void testSingleRegistration(){
        List<List<String>> valueLists = CSVReader.readValues("src/main/java/org/example/artifacts/teilnehmerliste.csv");
        List<String> firstEntry = valueLists.get(1);

        String expectedID = "004670cb-47f5-40a4-87d8-5276c18616ec";
        String expectedName = "Person1";
        String expectedFoodPreference = "veggie";
        String expectedAge = "21";
        String expectedSex = "male";
        String expectedKitchenType = "maybe";
        String expectedKitchenStory = "3.0";
        String expectedKitchenLongitude = "8.673368271555807";
        String expectedKitchenLatitude = "50.5941282715558";

        Assertions.assertEquals(expectedID, firstEntry.get(1));
        Assertions.assertEquals(expectedName, firstEntry.get(2));
        Assertions.assertEquals(expectedFoodPreference, firstEntry.get(3));
        Assertions.assertEquals(expectedAge, firstEntry.get(4));
        Assertions.assertEquals(expectedSex, firstEntry.get(5));
        Assertions.assertEquals(expectedKitchenType, firstEntry.get(6));
        Assertions.assertEquals(expectedKitchenStory, firstEntry.get(7));
        Assertions.assertEquals(expectedKitchenLongitude, firstEntry.get(8));
        Assertions.assertEquals(expectedKitchenLatitude, firstEntry.get(9));
    }

    @Test
    void testPairRegistration(){
        List<List<String>> valueLists = CSVReader.readValues("src/main/java/org/example/artifacts/teilnehmerliste.csv");
        List<String> firstEntry = valueLists.get(3);

        String expectedID = "01be5c1f-4aa1-458d-a530-b1c109ffbb55";
        String expectedName = "Person3";
        String expectedFoodPreference = "vegan";
        String expectedAge = "22";
        String expectedSex = "male";
        String expectedKitchenType = "yes";
        String expectedKitchenStory = "";
        String expectedKitchenLongitude = "8.681372017093311";
        String expectedKitchenLatitude = "50.5820794170933";
        String expectedPartnerID = "117ee996-14d3-44e8-8bcb-eb2d29fddda5";
        String expectedPartnerName = "Personx1";
        String expectedPartnerAge = "25.0";
        String expectedPartnerSex = "male";

        Assertions.assertEquals(expectedID,firstEntry.get(1));
        Assertions.assertEquals(expectedName, firstEntry.get(2));
        Assertions.assertEquals(expectedFoodPreference, firstEntry.get(3));
        Assertions.assertEquals(expectedAge, firstEntry.get(4));
        Assertions.assertEquals(expectedSex, firstEntry.get(5));
        Assertions.assertEquals(expectedKitchenType, firstEntry.get(6));
        Assertions.assertEquals(expectedKitchenStory, firstEntry.get(7));
        Assertions.assertEquals(expectedKitchenLongitude, firstEntry.get(8));
        Assertions.assertEquals(expectedKitchenLatitude, firstEntry.get(9));
        Assertions.assertEquals(expectedPartnerID, firstEntry.get(10));
        Assertions.assertEquals(expectedPartnerName, firstEntry.get(11));
        Assertions.assertEquals(expectedPartnerAge, firstEntry.get(12));
        Assertions.assertEquals(expectedPartnerSex, firstEntry.get(13));
    }
}