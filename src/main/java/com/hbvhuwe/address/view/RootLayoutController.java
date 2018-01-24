package com.hbvhuwe.address.view;

import com.hbvhuwe.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

public class RootLayoutController {
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonsFilePath(null);
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    @FXML
    private void handleSave() {
        if (mainApp.getPersonFilePath().isPresent()) {
            mainApp.savePersonDataToFile(mainApp.getPersonFilePath().get());
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AddressBook");
        alert.setHeaderText("About");
        alert.setContentText("Author: Chernonog Vladimir\nWebsite: https://github.com/hbvhuwe");
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
