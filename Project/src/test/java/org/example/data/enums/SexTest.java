package org.example.data.enums;

import org.example.data.tools.Keywords;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class SexTest {

    @Test
    void parseSexMale() {
        String male = Keywords.male;
        Assertions.assertEquals(Sex.MALE, Sex.parseSex(male));
    }

    @Test
    void parseSexFemale() {
        String female = Keywords.female;
        Assertions.assertEquals(Sex.FEMALE, Sex.parseSex(female));
    }

    @Test
    void parseSexOther() {
        String other = Keywords.other;
        Assertions.assertEquals(Sex.OTHER, Sex.parseSex(other));
    }

    @Test
    void parseSexInvalidTest() {
        assertThrows(IllegalStateException.class, () -> {
            Sex.parseSex("Unexpected Value");
        });
    }

}