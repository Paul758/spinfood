package org.example.data.enums;

import org.example.data.tools.Keywords;

public enum KitchenType {
    NO, YES, MAYBE;

    public static KitchenType parseKitchenType(String data) {
        return switch (data) {
            case Keywords.yesKitchen -> YES;
            case Keywords.noKitchen -> NO;
            case Keywords.maybeKitchen -> MAYBE;
            default -> throw new IllegalStateException("Unexpected value: " + data);
        };
    }
}
