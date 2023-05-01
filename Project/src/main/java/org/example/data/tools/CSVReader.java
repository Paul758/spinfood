package org.example.data.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class CSVReader {

    private static final String COMMA_DELIMITER = ",";
    public static Map<String, Integer> keyWordMap;

    public static List<List<String>> readValues(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            String[] keyWords = line.split(COMMA_DELIMITER, -1);
            keyWordMap = createKeyWordMap(keyWords);

            List<List<String>> rows = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] readInValues = line.split(COMMA_DELIMITER, -1);
                ArrayList<String> data = new ArrayList<>(List.of(readInValues));
                rows.add(data);
            }
            return rows;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Integer> createKeyWordMap(String[] line) {
        keyWordMap = new HashMap<>();
        for (int i = 0; i < line.length; i++) {
            keyWordMap.put(line[i], i);
        }
        return keyWordMap;
    }
}
