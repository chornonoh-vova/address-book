package com.hbvhuwe.address;

import com.hbvhuwe.address.model.Person;
import com.hbvhuwe.address.model.PersonListWrapper;
import com.hbvhuwe.address.view.PersonEditDialogController;
import com.hbvhuwe.address.view.PersonOverviewController;
import com.hbvhuwe.address.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.*;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.xml.bind.*;

public class MainApp extends Application {
  private Stage primaryStage;
  private BorderPane rootLayout;

  /**
   * Data of Persons
   */
  private ObservableList<Person> personData = FXCollections.observableArrayList();

  public MainApp() {}

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
   * Returns user preferences(last opened file)
   * @return file with saved data or empty Optional
   */
  public Optional<File> getPersonFilePath() {
    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
    String filePath = prefs.get("filePath", null);
    if (filePath != null) {
      return Optional.of(new File(filePath));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Saves path to file
   * @param file - file to save or null to delete saved
   */
  public void setPersonsFilePath(File file) {
    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
    if (file != null) {
      prefs.put("filePath", file.getPath());
      primaryStage.setTitle("AddressBook - " + file.getName());
    } else {
      prefs.remove("filePath");
      primaryStage.setTitle("AddressBook");
    }
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

      RootLayoutController controller = loader.getController();
      controller.setMainApp(this);

      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }

    getPersonFilePath().ifPresent((file) -> {
      loadPersonDataFromFile(file);
    });
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

  /**
   * Load address book from file
   * @param file
   */
  public void loadPersonDataFromFile(File file) {
    try {
      JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
      Unmarshaller um = context.createUnmarshaller();

      PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

      personData.clear();
      personData.addAll(wrapper.getPersons());

      setPersonsFilePath(file);
    } catch (JAXBException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Could not load data");
      alert.setContentText("Could not load data from file:\n" + file.getPath());

      alert.showAndWait();
    }
  }

  /**
   * Save address book to file
   * @param file
   */
  public void savePersonDataToFile(File file) {
    try {
      JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
      Marshaller m = context.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      PersonListWrapper wrapper = new PersonListWrapper();
      wrapper.setPersons(personData);

      m.marshal(wrapper, file);
      setPersonsFilePath(file);
    } catch (JAXBException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Could not save data");
      alert.setContentText("Could not save data to file:\n" + file.getPath());

      alert.showAndWait();
    }
  }
}
