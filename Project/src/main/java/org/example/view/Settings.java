package org.example.view;

import java.util.Locale;

public class Settings {
    private static Settings instance;
    private double fontSize;

    private Locale locale = Locale.GERMAN;

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

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    public Locale getLocale() {
        return this.locale;
    }
}
