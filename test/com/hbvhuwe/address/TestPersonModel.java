package com.hbvhuwe.address;

import com.hbvhuwe.address.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.Month;


public class TestPersonModel {
  @Test
  public void testSetters() {
    Person p1 = new Person();

    p1.setFirstName("firstName");
    p1.setLastName("lastName");
    p1.setCity("city");
    p1.setPostalCode(1234);
    p1.setBirthday(LocalDate.of(1998, Month.SEPTEMBER, 18));

    Assertions.assertEquals("firstName", p1.getFirstName());
    Assertions.assertEquals("lastName", p1.getLastName());
    Assertions.assertEquals(LocalDate.of(1998, Month.SEPTEMBER, 18), p1.getBirthday());
    Assertions.assertEquals("city", p1.getCity());
    Assertions.assertEquals(1234, p1.getPostalCode());
  }

  @Test
  public void testConstructor() {
    Person p1 = new Person();

    Assertions.assertNotNull(p1.firstNameProperty());
    Assertions.assertNotNull(p1.lastNameProperty());

    Assertions.assertNull(p1.getFirstName());
    Assertions.assertNull(p1.getLastName());
  }
}
