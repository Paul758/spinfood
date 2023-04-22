package Data;

public class Solo extends Participant {
    public final Person person;

    public Solo(Person person, FoodPreference foodPreference, Kitchen kitchen) {
        super(foodPreference, kitchen);
        this.person = person;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Solo solo = (Solo) obj;
        return this.person.equals(solo.person);
    }

    @Override
    public String toString() {
        return "Solo["
                + "person=" + person
                + ", foodPreference=" + foodPreference
                + ", kitchen=" + kitchen
                + "]";
    }
}
