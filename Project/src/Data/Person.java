package Data;

public record Person(String id, String name, int age, Sex sex) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Person person = (Person) obj;
        return this.id.equals(person.id);
    }

    @Override
    public String toString() {
        return "(" + name + ", " + age + ", " + sex + ")";
    }
}
