package Logic.Tools;

import Data.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    public static List<String[]> parseCSV(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();

        while (line != null) {
            line = line.replace(",", ", ");
            String[] row = line.split(",");
            for (int i = 0; i < row.length; i++) {
                row[i] = row[i].replace(" ", "");
            }
            data.add(row);
            line = br.readLine();
        }

        br.close();
        return data;
    }

    public static List<Participant> stringArraysToParticipantList(List<String[]> list) {
        List<Participant> participants = new ArrayList<>();
        for (String[] arr : list) {
            Participant participant = createParticipantFromArray(arr);
            participants.add(participant);
        }
        return participants;
    }

    private static Participant createParticipantFromArray(String[] arr) {
        if (arr[10].equals("")) {
            return createSoloFromArray(arr);
        } else {
            return createPairFromArray(arr);
        }
    }

    private static Person createPerson1FromArray(String[] arr) {
        String id = arr[1];
        String name = arr[2];
        int age = Integer.parseInt(arr[4]);
        Sex sex = stringToSex(arr[5]);

        return new Person(id, name, age, sex);
    }

    private static Person createPerson2FromArray(String[] arr) {
        String id = arr[10];
        String name = arr[11];
        int age = (int) parseFloat(arr[12]);
        Sex sex = stringToSex(arr[13]);

        return new Person(id, name, age, sex);
    }

    private static Solo createSoloFromArray(String[] arr) {
        Person person = createPerson1FromArray(arr);
        FoodPreference foodPreference = stringToFoodPreference(arr[3]);
        Kitchen kitchen = createKitchenFromArray(arr);

        return new Solo(person, foodPreference, kitchen);
    }

    private static Pair createPairFromArray(String[] arr) {
        Person person1 = createPerson1FromArray(arr);
        Person person2 = createPerson2FromArray(arr);
        FoodPreference foodPreference = stringToFoodPreference(arr[3]);
        Kitchen kitchen = createKitchenFromArray(arr);

        return new Pair(person1, person2, foodPreference, kitchen);
    }

    private static Kitchen createKitchenFromArray(String[] arr) {
        KitchenType kitchenType = stringToKitchenType(arr[6]);
        int kitchenStory = 0;
        if (!arr[7].equals("")) {
            kitchenStory = (int) parseFloat(arr[7]);
        }

        float longitude = parseFloat(arr[8]);
        float latitude = parseFloat(arr[9]);
        Coordinate coordinate = new Coordinate(longitude, latitude);

        return new Kitchen(kitchenType, kitchenStory, coordinate);
    }

    private static float parseFloat(String string) {
        if (string.equals("")) {
            return 0;
        } else {
            return Float.parseFloat(string);
        }
    }

    private static Sex stringToSex(String string) {
        return switch (string) {
            case "female" -> Sex.FEMALE;
            case "male" -> Sex.MALE;
            case "other" -> Sex.OTHER;
            default -> null;
        };
    }

    private static KitchenType stringToKitchenType(String string) {
        return switch (string) {
            case "yes" -> KitchenType.YES;
            case "no" -> KitchenType.NO;
            case "maybe" -> KitchenType.MAYBE;
            default -> null;
        };
    }

    private static FoodPreference stringToFoodPreference(String string) {
        return switch (string) {
            case "none" -> FoodPreference.NONE;
            case "meat" -> FoodPreference.MEAT;
            case "veggie" -> FoodPreference.VEGGIE;
            case "vegan" -> FoodPreference.VEGAN;
            default -> null;
        };
    }
}
