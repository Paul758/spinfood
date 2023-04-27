package org.example.data.enums;

public enum KitchenType {
    NO, YES, MAYBE;

    public static KitchenType parseKitchenType(String data){
        switch (data) {
            case "yes":
                return YES;
            case "no":
                return NO;
            case "maybe":
                return MAYBE;
        }
        throw new RuntimeException("Tried to parse an unexpected KitchenType: " + data + "Maybe there is an error in the .csv");
    }
}
