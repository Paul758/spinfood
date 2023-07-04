package org.example.data.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * This class reads in a csv file and returns a list of lists of strings.
 * Can be used to read all sorts of csv-files, not only given one, however filepath should be changed.
 * New Version: 1.3 This Class now contains a method to check if a file is a party location file.
 * Also contains a method to create a map of keywords and their given index in the csv file.
 * @author David Riemer
 * @version 1.2
 *

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

    /**
     * This method creates a map, in which every String keyword is mapped to its index in the csv file.
     * @param line the line of the csv file that contains the keywords
     * @return a map of keywords and their index in the csv file
     */
    public static Map<String, Integer> createKeyWordMap(List<String> line) {
        Map<String, Integer> keyWordMap = new HashMap<>();
        for (int i = 0; i < line.size(); i++) {
            keyWordMap.put(line.get(i), i);
        }
        return keyWordMap;
    }

    public static boolean isPartyLocation(File file) {
        List<List<String>> readInValues = readValues(file.getAbsolutePath());

        List<String> headerValues = readInValues.get(0);

        if(headerValues.contains(Keywords.id)) {
            return false;
        }

        if(headerValues.contains(Keywords.longitude) && headerValues.contains(Keywords.latitude)) {
            System.out.println("This file is a party location");
            return true;
        }

        throw new IllegalStateException("The chosen file is not identified as party location, the headers in this file are " + headerValues.toString());
    }
}
