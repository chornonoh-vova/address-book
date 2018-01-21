package com.hbvhuwe.address;

import com.hbvhuwe.address.model.Person;
import com.hbvhuwe.address.view.PersonEditDialogController;
import com.hbvhuwe.address.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.*;

import java.io.IOException;

import javax.swing.ImageIcon;

public class MainApp extends Application {
  private Stage primaryStage;
  private BorderPane rootLayout;

  /**
   * Data of Persons
   */
  private ObservableList<Person> personData = FXCollections.observableArrayList();

  public MainApp() {
    personData.add(new Person("Hans", "Muster"));
    personData.add(new Person("Ruth", "Mueller"));
    personData.add(new Person("Heinz", "Kurz"));
    personData.add(new Person("Cornelia", "Meier"));
    personData.add(new Person("Werner", "Meyer"));
    personData.add(new Person("Lydia", "Kunz"));
    personData.add(new Person("Anna", "Best"));
    personData.add(new Person("Stefan", "Meier"));
    personData.add(new Person("Martin", "Mueller"));
  }

  public ObservableList<Person> getPersonData() {
    return personData;
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("AddressApp");

    this.primaryStage.getIcons().add(new Image(ClassLoader.getSystemResource("images/address-book-icon.png").toString()));

    initRootLayout();
    
    showPersonOverview();
  }

  /**
   * Initialize root component
   */
  public void initRootLayout() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ClassLoader.getSystemResource("view/RootLayout.fxml"));
      rootLayout = loader.load();

      Scene scene = new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Opens dialog window
   */
  public boolean showPersonEditDialog(Person person) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ClassLoader.getSystemResource("view/PersonEditDialog.fxml"));
      AnchorPane page = loader.load();

      Stage dialogStage = new Stage();
      dialogStage.setTitle("Edit person");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.initOwner(primaryStage);

      Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      PersonEditDialogController controller = loader.getController();
      controller.setDialogStage(dialogStage);
      controller.setPerson(person);

      dialogStage.showAndWait();
      return controller.isOkClicked();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Show person overview in root component
   */
  public void showPersonOverview() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ClassLoader.getSystemResource("view/PersonOverview.fxml"));
      AnchorPane personOverview = loader.load();

      rootLayout.setCenter(personOverview);

      PersonOverviewController controller = loader.getController();
      controller.setMainApp(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
