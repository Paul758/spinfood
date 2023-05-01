package org.example;

import org.example.data.*;

public class Main {
    public static void main(String[] args) {

        String fileToRead = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        DataManagement dataManagement = new DataManagement(fileToRead);

        dataManagement.printParticipants();
        System.out.println("Solo participants");
        dataManagement.printSoloParticipants();
        System.out.println("Pair participants");
        dataManagement.printPairParticipants();
    }
}