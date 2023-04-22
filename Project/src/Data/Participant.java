package Data;

public abstract class Participant {
    public final FoodPreference foodPreference;
    public final Kitchen kitchen;

    public Participant(FoodPreference foodPreference, Kitchen kitchen) {
        this.foodPreference = foodPreference;
        this.kitchen = kitchen;
    }
}
