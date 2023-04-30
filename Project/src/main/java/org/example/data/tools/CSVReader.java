package org.example.data.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {

    private static final String COMMA_DELIMITER = ",";

    public static List<List<String>> readValues(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<List<String>> rows = new ArrayList<>();
            skipLine(br);
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] readInValues = line.split(COMMA_DELIMITER, -1);

                ArrayList<String> data = new ArrayList<>(List.of(readInValues));
                System.out.println(Arrays.deepToString(readInValues));
                data.remove(0);
                System.out.println("data list :" + data);

                rows.add(data);
            }

            return rows;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void skipLine(BufferedReader bufferedReader){
        try {
            bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
