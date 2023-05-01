package org.example.data;

import org.example.data.*;
import org.example.data.factory.DataFactory;
import org.example.data.structures.EventParticipant;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.data.tools.CSVReader;

import java.util.ArrayList;
import java.util.List;


// Gateway für Logic Schicht
public class DataManagement {

    ArrayList<EventParticipant> participantList = new ArrayList<>();
    ArrayList<Solo> soloParticipants = new ArrayList<>();
    ArrayList<Pair> pairParticipants = new ArrayList<>();

    public DataManagement(String filePath) {
        DataFactory dataFactory = new DataFactory();
        List<List<String>> csvDataList = CSVReader.readValues(filePath);

        for (List<String> valueLine : csvDataList) {
            EventParticipant participant = dataFactory.createDataFromLine(valueLine);
            participantList.add(participant);

            if (participant instanceof Solo){
                soloParticipants.add((Solo) participant);
            } else if (participant instanceof Pair){
                pairParticipants.add((Pair) participant);
            }
        }
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
