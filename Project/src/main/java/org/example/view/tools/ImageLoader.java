package org.example.view.tools;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class ImageLoader {
    private static ImageLoader instance;
    HashMap<String, Image> map;
    String noneIconPath = "none.png";
    String meatIconPath = "meat.png";
    String veggieIconPath = "veggie.png";
    String veganIconPath = "vegan.png";

    private ImageLoader() {
        map = new HashMap<>();
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    private ImageView getImage(String path) {
        if (!map.containsKey(path)) {
            map.put(path, new Image(path));
        }

        ImageView imageView = new ImageView(map.get(path));
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        return imageView;
    }

    public ImageView getNoneIcon() {
        ImageView imageView = getImage(noneIconPath);
        imageView.setUserData(0);
        return imageView;
    }

    public ImageView getMeatIcon() {
        ImageView imageView = getImage(meatIconPath);
        imageView.setUserData(1);
        return imageView;
    }

    public ImageView getVeggieIcon() {
        ImageView imageView = getImage(veggieIconPath);
        imageView.setUserData(2);
        return imageView;
    }

    public ImageView getVeganIcon() {
        ImageView imageView = getImage(veganIconPath);
        imageView.setUserData(3);
        return imageView;
    }
}
