package org.example.data;

import org.example.data.*;
import org.example.data.factory.CoordinateFactory;
import org.example.data.factory.DataFactory;
import org.example.data.structures.EventParticipant;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.data.tools.CSVReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


// Gateway für Logic Schicht
public class DataManagement {

    public ArrayList<EventParticipant> participantList = new ArrayList<>();
    public ArrayList<Solo> soloParticipants = new ArrayList<>();
    public ArrayList<Pair> pairParticipants = new ArrayList<>();

    public Coordinate partyLocation;


    public DataManagement(String filePathParticipants, String filePathLocation) {
        List<List<String>> csvDataList = CSVReader.readValues(filePathParticipants);
        List<String> csvHeader = csvDataList.get(0);
        Map<String, Integer> keywordMap = CSVReader.createKeyWordMap(csvHeader);
        csvDataList.remove(0);

        for (List<String> valueLine : csvDataList) {
            EventParticipant participant = DataFactory.createDataFromLine(valueLine, keywordMap);
            participantList.add(participant);

            if (participant instanceof Solo){
                soloParticipants.add((Solo) participant);
            } else if (participant instanceof Pair){
                pairParticipants.add((Pair) participant);
            }
        }


        List<List<String>> partyLocationData = CSVReader.readValues(filePathLocation);
        List<String> csvHeaderLocation = partyLocationData.get(0);
        Map<String, Integer> keywordMapLocation = CSVReader.createKeyWordMap(csvHeaderLocation);
        partyLocationData.remove(0);
        partyLocation = CoordinateFactory.createCoordinate(partyLocationData.get(0), keywordMapLocation);

    }

    public void printParticipants(){
        participantList.forEach(System.out::println);
    }

    public void printSoloParticipants(){
        soloParticipants.forEach(System.out::println);
    }

    public void printPairParticipants(){
        pairParticipants.forEach(System.out::println);
    }
}
