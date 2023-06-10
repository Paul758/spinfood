package org.example.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.data.tools.CSVReader;

import java.io.File;
import java.util.List;

public class ApplicationEntry extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Empty Frame");

        Group root = new Group(); // or use StackPane
        Scene scene = new Scene(root, 800, 200);

        primaryStage.setScene(scene);
        primaryStage.show();

        setupFileChooser(primaryStage);
    }

    private void setupFileChooser(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
        File partyLocation = new File("-1");
        File participantList = new File("-1");
        if (selectedFiles != null) {
           // System.out.println("Selected files");


            for (File file : selectedFiles) {
                if(CSVReader.isPartyLocation(file)) {
                    //System.out.println("This file is the party location");
                    //System.out.println(file.getAbsolutePath());
                    partyLocation = file;
                } else {
                    participantList = file;
                }
                //System.out.println(file.getAbsolutePath());

            }
        } else {
            System.out.println("no files selected");
        }
        System.out.println("The party location is " + partyLocation.getAbsolutePath());
        System.out.println("The participant list is " + participantList.getAbsolutePath());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
