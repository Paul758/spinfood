package org.example.data.enums;

import org.example.data.tools.Keywords;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class KitchenTypeTest {

    @Test
    void parseKitchenTypeYes() {
        String yes = Keywords.yesKitchen;
        Assertions.assertEquals(KitchenType.YES, KitchenType.parseKitchenType(yes));
    }

    @Test
    void parseKitchenTypeNo() {
        String no = Keywords.noKitchen;
        Assertions.assertEquals(KitchenType.NO, KitchenType.parseKitchenType(no));
    }

    @Test
    void parseKitchenTypeMaybe() {
        String maybe = Keywords.maybeKitchen;
        Assertions.assertEquals(KitchenType.MAYBE, KitchenType.parseKitchenType(maybe));
    }


}