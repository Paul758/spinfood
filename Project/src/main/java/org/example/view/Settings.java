package org.example.view;

public class Settings {
    private static Settings instance;
    private double fontSize;

    private Settings() {
        fontSize = 20;
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public double getFontsize() {
        return fontSize;
    }
}
