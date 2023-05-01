package org.example.data.enums;

public enum Sex {
    MALE, FEMALE, OTHER;

    public static Sex parseSex(String data){
        switch (data) {
            case "male":
                return MALE;
            case "female":
                return FEMALE;
            case "other":
                return OTHER;
        }
        throw new RuntimeException("Tried to parse an unexpected sex: " + data + "Maybe there is an error in the .csv");
    }
}
