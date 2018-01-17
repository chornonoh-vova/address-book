package com.hbvhuwe.address;

import com.hbvhuwe.address.model.Person;
import org.junit.Test;
import static org.junit.Assert.*;

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

    assertEquals("firstName", p1.getFirstName());
    assertEquals("lastName", p1.getLastName());
    assertEquals(LocalDate.of(1998, Month.SEPTEMBER, 18), p1.getBirthday());
    assertEquals("city", p1.getCity());
    assertEquals(1234, p1.getPostalCode());
  }

  @Test
  public void testConstructor() {
    Person p1 = new Person();

    assertNotNull(p1.firstNameProperty());
    assertNotNull(p1.lastNameProperty());

    assertNull(p1.getFirstName());
    assertNull(p1.getLastName());
  }
}
