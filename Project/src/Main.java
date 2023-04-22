import Data.Participant;
import Logic.Tools.CSVParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "src/Artifacts/teilnehmerliste.csv";
        List<String[]> data = CSVParser.parseCSV(path);
        for (String[] row : data) {
            System.out.println(Arrays.toString(row));
        }
        data.remove(0);

        List<Participant> participants = CSVParser.stringArraysToParticipantList(data);

        for (Participant participant : participants) {
            System.out.println(participant);
        }
    }
}