package org.example;

import org.example.data.*;

public class Main {
    public static void main(String[] args) {

        DataManagement.getInstance().setUp();
        DataManagement.getInstance().printParticipants();
        System.out.println("Solo participants");
        DataManagement.getInstance().printSoloParticipants();
        System.out.println("Pair participants");
        DataManagement.getInstance().printPairParticipants();
    }
}