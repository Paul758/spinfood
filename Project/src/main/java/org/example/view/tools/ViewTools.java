package org.example.view.tools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.data.enums.FoodPreference;
import org.example.data.structures.Solo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewTools {
    public static ImageView getFoodPreferenceIcon(FoodPreference foodPreference) {
        String path = switch (foodPreference) {
            case MEAT -> "meat.png";
            case NONE -> "none.png";
            case VEGGIE -> "veggie.png";
            case VEGAN -> "vegan.png";
        };

        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        return imageView;
    }

    public static String getTimeStamp() {
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timestamp.format(formatter);
    }

    public static String getSoloData(Solo solo) {
        return solo.getPerson().name() + "\n"
                + solo.getPerson().age() + "\n"
                + solo.getPerson().sex() + "\n";

    }
}
