package org.example.data.factory;

import org.example.data.enums.Sex;

/** Data record to hold the values of a person in the .csv file
 * @param id hash value to identify a person
 * @param name the person's name
 * @param age the person's age
 * @param sex the person's sex
 */
public record Person(String id, String name, int age, Sex sex) {

    @Override
    public String toString() {
        return "[" + "id='" + id + '\'' + ", name='" + name + '\'' + ", age=" + age + ", sex=" + sex + "] ";
    }
}
