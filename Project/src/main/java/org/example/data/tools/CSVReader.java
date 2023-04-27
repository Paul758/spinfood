package org.example.data.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {

    private static final CSVReader csvReader = new CSVReader();
    public static CSVReader getInstance(){
        return csvReader;
    }

    private final List<List<String>> csvDataList = new ArrayList<>();
    private static final String COMMA_DELIMITER = ",";
    private static final String fileToRead = "src/main/java/org/example/artifacts/teilnehmerliste.csv";

    public void readValues() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileToRead))) {
            skipLine(br);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] readInValues = line.split(COMMA_DELIMITER, -1);

                ArrayList<String> data = new ArrayList<>(List.of(readInValues));
                System.out.println(Arrays.deepToString(readInValues));
                data.remove(0);
                System.out.println("data list :" + data);

                csvDataList.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void skipLine(BufferedReader bufferedReader){
        try {
            bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<List<String>> getCsvDataList() {
        return csvDataList;
    }
}
