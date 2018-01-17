package com.hbvhuwe.address.view;

import com.hbvhuwe.address.model.Person;
import com.hbvhuwe.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonEditDialogController {
  @FXML
  private TextField firstNameField;
  @FXML
  private TextField lastNameField;
  @FXML
  private TextField streetField;
  @FXML
  private TextField postalCodeField;
  @FXML
  private TextField cityField;
  @FXML
  private TextField birthdayField;

  private Stage dialogStage;
  private Person person;
  private boolean okClicked = false;

  @FXML
  private void initialize() {}

  public void setDialogStage(Stage dialogStage) {
    this.dialogStage = dialogStage;
  }

  public void setPerson(Person person) {
    this.person = person;

    firstNameField.setText(person.getFirstName());
    lastNameField.setText(person.getLastName());
    streetField.setText(person.getStreet());
    postalCodeField.setText(Integer.toString(person.getPostalCode()));
    cityField.setText(person.getCity());
    birthdayField.setText(DateUtil.format(person.getBirthday()).get());
    birthdayField.setPromptText("dd.mm.yyyy");
  }

  public boolean isOkClicked() {
    return okClicked;
  }

  @FXML
  private void handleOk() {
    if (isInputValid()) {
      person.setFirstName(firstNameField.getText());
      person.setLastName(lastNameField.getText());
      person.setStreet(lastNameField.getText());
      person.setCity(cityField.getText());
      person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
      person.setBirthday(DateUtil.parse(birthdayField.getText()).get());

      okClicked = true;
      dialogStage.close();
    }
  }

  @FXML
  private void handleCancel() {
    dialogStage.close();
  }

  private boolean isInputValid() {
    StringBuffer errorMessage = new StringBuffer();
    if (firstNameField.getText() == null || firstNameField.getText().isEmpty()) {
      errorMessage.append("Empty first name\n");
    }

    if (lastNameField.getText() == null || lastNameField.getText().isEmpty()) {
      errorMessage.append("Empty last name\n");
    }

    if (streetField.getText() == null || streetField.getText().isEmpty()) {
      errorMessage.append("Empty valid street\n");
    }

    if (postalCodeField.getText() == null || postalCodeField.getText().isEmpty()) {
      errorMessage.append("Empty postal code\n");
    } else {
      try {
        Integer.parseInt(postalCodeField.getText());
      } catch (NumberFormatException e) {
        errorMessage.append("Not valid postal code\n");
      }
    }

    if (cityField.getText() == null || cityField.getText().isEmpty()) {
      errorMessage.append("Empty city\n");
    }

    if (birthdayField.getText() == null || birthdayField.getText().isEmpty()) {
      errorMessage.append("Empty birthday");
    } else {
      if (!DateUtil.validDate(birthdayField.getText())) {
        errorMessage.append("Not valid birthday. Use dd.mm.yyy format");
      }
    }

    if (errorMessage.toString().isEmpty()) {
      return true;
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.initOwner(dialogStage);
      alert.setTitle("Invalid fields");
      alert.setHeaderText("Please correct invalid fields");
      alert.setContentText(errorMessage.toString());

      alert.showAndWait();

      return false;
    }
  }

}
