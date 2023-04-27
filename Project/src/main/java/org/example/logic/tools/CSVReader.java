package org.example.logic.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {

    private static final String COMMA_DELIMITER = ",";
    private static final String fileToRead = "src/Artifacts/teilnehmerliste.csv";
    public List<List<String>> readCSV() throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileToRead))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        }
        return records;
    }
    public static void main(String[] args) {
        CSVReader main = new CSVReader();
        //print the list to console
        try {
            System.out.println(main.readCSV());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
