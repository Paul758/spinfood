package org.example.data.enums;

import org.example.data.tools.Keywords;

public enum Sex {
    MALE, FEMALE, OTHER;

    public static Sex parseSex(String data) {
        return switch (data) {
            case Keywords.male -> MALE;
            case Keywords.female -> FEMALE;
            case Keywords.other -> OTHER;
            default -> throw new IllegalStateException("Unexpected value: " + data);
        };
    }
}
