package com.hbvhuwe.address.model;

import com.hbvhuwe.address.util.LocalDateAdapter;
import javafx.beans.property.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * Model class for person
 */
public class Person {
  private final StringProperty firstName;
  private final StringProperty lastName;
  private final StringProperty street;
  private final IntegerProperty postalCode;
  private final StringProperty city;
  private final ObjectProperty<LocalDate> birthday;

  /**
   * Default constructor
   */
  public Person() {
    this(null, null);
  }

  /**
   * Constructor with some initial data
   * @param firstName
   * @param lastName
   */
  public Person(String firstName, String lastName) {
    this.firstName = new SimpleStringProperty(firstName);
    this.lastName = new SimpleStringProperty(lastName);

    this.street = new SimpleStringProperty("street");
    this.postalCode = new SimpleIntegerProperty(1111);
    this.city = new SimpleStringProperty("city");
    this.birthday = new SimpleObjectProperty<>(LocalDate.of(1970, 1, 1));
  }

  public String getFirstName() {
    return firstName.get();
  }

  public StringProperty firstNameProperty() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName.set(firstName);
  }

  public String getLastName() {
    return lastName.get();
  }

  public StringProperty lastNameProperty() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName.set(lastName);
  }

  public String getStreet() {
    return street.get();
  }

  public StringProperty streetProperty() {
    return street;
  }

  public void setStreet(String street) {
    this.street.set(street);
  }

  public int getPostalCode() {
    return postalCode.get();
  }

  public IntegerProperty postalCodeProperty() {
    return postalCode;
  }

  public void setPostalCode(int postalCode) {
    this.postalCode.set(postalCode);
  }

  public String getCity() {
    return city.get();
  }

  public StringProperty cityProperty() {
    return city;
  }

  public void setCity(String city) {
    this.city.set(city);
  }

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
  public LocalDate getBirthday() {
    return birthday.get();
  }

  public ObjectProperty<LocalDate> birthdayProperty() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday.set(birthday);
  }
}
