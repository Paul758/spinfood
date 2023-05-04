package org.example.data.enums;

import org.example.data.tools.Keywords;

/**
 * Enum to represent the availability of a kitchen of a person
 */
public enum KitchenType {
    NO, YES, MAYBE;

    /**
     * Converts the String value of Kitchen-availability to the corresponding enum value
     * @author Paul Groß
     * @param data data from the csv file
     * @return the corresponding enum value
     * @throws IllegalStateException if the data is not a valid kitchen-availability
     */
    public static KitchenType parseKitchenType(String data) {
        return switch (data) {
            case Keywords.yesKitchen -> YES;
            case Keywords.noKitchen -> NO;
            case Keywords.maybeKitchen -> MAYBE;
            default -> throw new IllegalStateException("Unexpected value: " + data);
        };
    }
}
