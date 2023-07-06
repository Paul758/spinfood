package org.example.view.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

import java.util.Locale;

/**
 * handles the saving and loading of the language preference
 */
public class Settings {
    private static Settings instance;
    private Locale locale;

    private Settings() {
        this.locale = Locale.US;
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public Locale getLocale() {
        return this.locale;
    }

    /**
     * saves a given Local object into a json file
     * @param locale a Local Objects that contains the current selected language
     */
    public void saveLanguage(Locale locale) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            objectMapper.writeValue(new File("settings.json"), locale.toLanguageTag());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads the language to be used from a json file
     */
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
