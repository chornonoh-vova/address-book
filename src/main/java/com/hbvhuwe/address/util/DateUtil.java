package com.hbvhuwe.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class DateUtil {
  private static final String DATE_PATTERN = "dd.MM.yyyy";

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

  public static Optional<String> format(LocalDate date) {
    if (date == null) {
      return Optional.empty();
    }
    return Optional.of(DATE_FORMATTER.format(date));
  }

  public static Optional<LocalDate> parse(String dateString) {
    try {
      return Optional.of(DATE_FORMATTER.parse(dateString, LocalDate::from));
    } catch (DateTimeParseException e) {
      return Optional.empty();
    }
  }

  public static boolean validDate(String dateString) {
    return DateUtil.parse(dateString).isPresent();
  }
}
