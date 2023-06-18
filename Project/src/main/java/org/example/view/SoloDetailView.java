package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.data.structures.Solo;
import org.example.view.tools.PairBuilder;

import java.io.IOException;

public class SoloDetailView {

    @FXML
    Text nt, ndt, at, adt, fpt, fpdt;

    public void displaySolo(Solo solo) {
        ndt.setText(solo.getPerson().name());
        adt.setText(String.valueOf(solo.getPerson().age()));
        fpdt.setText(solo.getFoodPreference().toString());
    }

    public void setup() {
        double fontsize = Settings.getInstance().getFontsize();
        Font font = new Font(fontsize);
        nt.setFont(font);
        ndt.setFont(font);
    }

    public static SoloDetailView createNewSoloDetailView(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(PairBuilder.class.getResource("/SoloDetailView.fxml"));
        Parent root = fxmlLoader.load();
        anchorPane.getChildren().add(root);

        AnchorPane.setLeftAnchor(root, 0d);
        AnchorPane.setRightAnchor(root, 0d);
        AnchorPane.setBottomAnchor(root, 0d);
        AnchorPane.setTopAnchor(root, 0d);

        return fxmlLoader.getController();
    }
}
