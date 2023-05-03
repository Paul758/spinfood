package org.example;

// SuperMain is needed, because a jar executable needs a classpath to a Main class which doesn't extend another class
// The only purpose of this class is to call the main Method of the Main that extends the Application class for JavaFX
public class SuperMain {
    public static void main(String[] args) {
        Main.main(args);
    }
}
