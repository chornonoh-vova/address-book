package com.hbvhuwe.address.view;

import com.hbvhuwe.address.MainApp;
import com.hbvhuwe.address.model.Person;
import com.hbvhuwe.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
  @FXML
  private TableView<Person> personTable;
  @FXML
  private TableColumn<Person, String> firstNameColumn;
  @FXML
  private TableColumn<Person, String> lastNameColumn;

  @FXML
  private Label firstNameLabel;
  @FXML
  private Label lastNameLabel;
  @FXML
  private Label streetLabel;
  @FXML
  private Label postalCodeLabel;
  @FXML
  private Label cityLabel;
  @FXML
  private Label birthdayLabel;

  private MainApp mainApp;

  public PersonOverviewController() {

  }

  @FXML
  private void initialize() {
    firstNameColumn.setCellValueFactory(cellData ->
        cellData.getValue().firstNameProperty());
    lastNameColumn.setCellValueFactory(cellData ->
        cellData.getValue().lastNameProperty());

    showPersonDetails(null);

    personTable.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
  }

  public void setMainApp(MainApp mainApp) {
    this.mainApp = mainApp;

    personTable.setItems(mainApp.getPersonData());
  }

  private void showPersonDetails(Person person) {
    if (person != null) {
      firstNameLabel.setText(person.getFirstName());
      lastNameLabel.setText(person.getLastName());
      streetLabel.setText(person.getStreet());
      cityLabel.setText(person.getCity());
      postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
      birthdayLabel.setText(DateUtil.format(person.getBirthday()).get());
    } else {
      firstNameLabel.setText("");
      lastNameLabel.setText("");
      streetLabel.setText("");
      postalCodeLabel.setText("");
      cityLabel.setText("");
      birthdayLabel.setText("");
    }
  }

  @FXML
  private void handleNewPerson() {
    Person person = new Person();
    boolean okClicked = mainApp.showPersonEditDialog(person);
    if (okClicked) {
      mainApp.getPersonData().add(person);
    }
  }

  @FXML
  private void handleEditPerson() {
    Person selected = personTable.getSelectionModel().getSelectedItem();

    if (selected != null) {
      boolean okClicked = mainApp.showPersonEditDialog(selected);
      if (okClicked) {
        showPersonDetails(selected);
      }
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.initOwner(mainApp.getPrimaryStage());
      alert.setTitle("No selection");
      alert.setHeaderText("No person selected");
      alert.setContentText("Please select a person in the table.");

      alert.showAndWait();
    }
  }

  @FXML
  private void handleDeletePerson() {
    int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
      personTable.getItems().remove(selectedIndex);
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.initOwner(mainApp.getPrimaryStage());
      alert.setTitle("No selection");
      alert.setHeaderText("No person selected");
      alert.setContentText("Please select a person in a table.");

      alert.showAndWait();
    }
  }
}
