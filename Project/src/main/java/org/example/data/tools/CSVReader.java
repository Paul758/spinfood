package org.example.data.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * This class reads in a csv file and returns a list of lists of strings.
 * Can be used to read all sorts of csv-files, not only given one, however filepath should be changed.
 * @author David Riemer
 */
public class CSVReader {

    private static final String COMMA_DELIMITER = ",";

    public static List<List<String>> readValues(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

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

    public static Map<String, Integer> createKeyWordMap(List<String> line) {
        Map<String, Integer> keyWordMap = new HashMap<>();
        for (int i = 0; i < line.size(); i++) {
            keyWordMap.put(line.get(i), i);
        }
        return keyWordMap;
    }
}
