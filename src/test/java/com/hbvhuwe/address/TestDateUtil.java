package com.hbvhuwe.address;

import com.hbvhuwe.address.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RunWith(Parameterized.class)
public class TestDateUtil {
  private String incorrect;
  private String correct;

  @Parameterized.Parameters
  public static Collection dates() {
    return Arrays.asList(new String[][] {
        {"23.144.12455", "12.12.1222"},
        {"676767/532632/27838", "23.12.1234"},
        {"91.91.5672", "19.09.1998"},
        {"76.78.7282", "01.01.2673"}
    });
  }

  public TestDateUtil(String incorrect, String correct) {
    this.incorrect = incorrect;
    this.correct = correct;
  }

  @Test
  public void testDates() {
    Assert.assertEquals(DateUtil.parse(incorrect), Optional.empty());
    Assert.assertTrue(DateUtil.parse(correct).isPresent());
    Assert.assertFalse(DateUtil.validDate(incorrect));
    Assert.assertTrue(DateUtil.validDate(correct));
  }
}
