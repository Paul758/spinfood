package org.example;

import org.example.data.*;

import org.example.logic.structures.MatchingRepository;

public class Main {
    public static void main(String[] args) {
        String fileToRead = "Project/src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String partyLocation = "Project/src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(fileToRead, partyLocation);
        MatchingRepository matchingRepository = new MatchingRepository(dataManagement);

        //print results
        //pairs
        System.out.println("pair count");
        System.out.println(matchingRepository.getMatchedPairsCollection().size());

        //groups
        System.out.println("groups count");
        System.out.println(matchingRepository.getMatchedGroupsCollection().size());


        //unmatched solos
        System.out.println("unmatched solos size");
        System.out.println(matchingRepository.soloSuccessors.size());

        //unmatched pairs
        System.out.println("unmatched pairs size");
        System.out.println(matchingRepository.pairSuccessors.size());

        System.out.println("Matched groups total " + matchingRepository.getMatchedGroupsCollection().size());
    }
}