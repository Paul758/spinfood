package org.example.view.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

import java.util.Locale;

public class Settings {
    private static Settings instance;
    private double fontSize;
    private Locale locale;

    private Settings() {
        fontSize = 20;
        this.locale = Locale.US;
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

    public Locale getLocale() {
        return this.locale;
    }


    public void saveLanguage(Locale locale) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            objectMapper.writeValue(new File("settings.json"), locale.toLanguageTag());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPreferences() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String localeTag = objectMapper.readValue(new File("settings.json"), String.class);
            locale = Locale.forLanguageTag(localeTag);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
