package org.example.view.tools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.data.enums.FoodPreference;

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
}
