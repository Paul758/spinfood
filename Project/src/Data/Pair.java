package Data;

public class Pair extends Participant {
    public final Person person1;
    public final Person person2;

    public Pair(Person person1, Person person2, FoodPreference foodPreference, Kitchen kitchen) {
        super(foodPreference, kitchen);
        this.person1 = person1;
        this.person2 = person2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Pair pair = (Pair) obj;
        return this.person1.equals(pair.person1) && this.person2.equals(pair.person2)
                || this.person1.equals(pair.person2) && this.person2.equals(pair.person1);
    }

    @Override
    public String toString() {
        return "Pair["
                + "person1=" + person1
                + ", person2=" + person2
                + ", foodPreference=" + foodPreference
                + ", kitchen=" + kitchen
                + "]";
    }
}
