package org.example.view.tools;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.enums.Sex;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;
import org.example.data.structures.Solo;
import org.example.view.properties.SoloProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TableViewToolsTest {

    @Test
    void map() {
        Solo solo = new Solo(
                new Person("1234", "name", 20, Sex.MALE),
                FoodPreference.NONE,
                new Kitchen(KitchenType.MAYBE, 1, 0.0, 0.0));

        List<Solo> soloList = List.of(solo);
        List<SoloProperty> propertyList = TableViewTools.map(soloList, SoloProperty::new);
        Assertions.assertEquals(soloList.get(0), propertyList.get(0).solo());
    }
}