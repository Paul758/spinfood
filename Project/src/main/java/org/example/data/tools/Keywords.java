package org.example.data.tools;

/**
 * Data holding class for keywords that appear in the csv file. Contains the column header keywords and keywords for the enums FoodPreference, KitchenType, Sex
 * @author Paul Groß
 * @see org.example.data.enums.FoodPreference
 * @see org.example.data.enums.KitchenType
 * @see org.example.data.enums.Sex
 */
public class Keywords {

    //CSV Header column keywords
    public static final String id = "ID";
    public static final String name = "Name";
    public static final String foodPreference = "FoodPreference";
    public static final String age = "Age";
    public static final String sex = "Sex";
    public static final String kitchen = "Kitchen";
    public static final String kitchenStory = "Kitchen_Story";
    public static final String kitchenLongitude = "Kitchen_Longitude";
    public static final String kitchenLatitude = "Kitchen_Latitude";
    public static final String idPartner = "ID_2";
    public static final String namePartner = "Name_2";
    public static final String agePartner = "Age_2";
    public static final String sexPartner = "Sex_2";


    //CSV enum keywords
    public static final String meat = "meat";
    public static final String veggie = "veggie";
    public static final String vegan = "vegan";
    public static final String none = "none";


    public static final String male = "male";
    public static final String female = "female";
    public static final String other = "other";

    public static final String noKitchen = "no";
    public static final String yesKitchen = "yes";
    public static final String maybeKitchen = "maybe";


    //Party Location Keywords
    public static final String longitude = "Longitude";
    public static final String latitude = "Latitude";
}
