package org.example.data.enums;

import org.example.data.tools.Keywords;

public enum Sex {
    MALE, FEMALE, OTHER;

    /**
     * Converts the String value of sex from the csv to the corresponding enum value
     * @author Paul Groß
     * @param data data from the csv file
     * @return the corresponding enum value
     * @throws IllegalStateException if the data is not a valid sex
     */
    public static Sex parseSex(String data) {
        return switch (data) {
            case Keywords.male -> MALE;
            case Keywords.female -> FEMALE;
            case Keywords.other -> OTHER;
            default -> throw new IllegalStateException("Unexpected value: " + data);
        };
    }
}
