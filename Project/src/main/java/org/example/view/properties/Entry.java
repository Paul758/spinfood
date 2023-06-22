package org.example.view.properties;

public class Entry {
    public EntryType entryType;
    public String name;

    public Entry(EntryType entryType, String name) {
        this.entryType = entryType;
        this.name = name;
    }

    public Entry(String name) {
        this.entryType = EntryType.STRING;
        this.name = name;
    }
}
