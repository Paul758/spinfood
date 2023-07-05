package org.example.view.tools;

import javafx.scene.image.ImageView;
import org.example.data.enums.FoodPreference;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewTools {

    /**
     * Gets the associated image of a given food preference
     * @param foodPreference a food preference
     * @return the image that represents the food preference
     */
    public static ImageView getFoodPreferenceIcon(FoodPreference foodPreference) {
        return switch (foodPreference) {
            case MEAT -> ImageLoader.getInstance().getMeatIcon();
            case NONE -> ImageLoader.getInstance().getNoneIcon();
            case VEGGIE -> ImageLoader.getInstance().getVeggieIcon();
            case VEGAN -> ImageLoader.getInstance().getVeganIcon();
        };
    }

    /**
     * Get the current time in the HH:mm:ss format
     * @return the current time as string
     */
    public static String getTimeStamp() {
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timestamp.format(formatter);
    }
}
