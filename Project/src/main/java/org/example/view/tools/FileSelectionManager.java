package org.example.view.tools;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.data.tools.CSVReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSelectionManager {
    Stage primaryStage;

    public FileSelectionManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public List<File> selectFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        List<File> dataFiles = new ArrayList<>();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
        File partyLocation = new File("-1");
        File participantList = new File("-1");
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                if (CSVReader.isPartyLocation(file)) {
                    partyLocation = file;
                } else {
                    participantList = file;
                }
            }
        } else {
            System.out.println("no files selected");
        }

        dataFiles.add(participantList);
        dataFiles.add(partyLocation);
        return dataFiles;
    }
}