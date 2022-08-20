package com.trade.data.enricher.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateUtils {

  // Date format of the date in the input trade csv.
  // yyyy vs uuuu - https://stackoverflow.com/questions/41177442/uuuu-versus-yyyy-in-datetimeformatter-formatting-pattern-codes-in-java
  public static final String dateFormat = "uuuuMMdd";

  /**
   * Checks if input string is valid date formatted.
   *
   * @param inDate input date
   * @return true/false if date is in the right format
   */
  public static boolean isValidDate(String inDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat)
        .withResolverStyle(ResolverStyle.STRICT);
    try {
      LocalDate.parse(inDate, formatter);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

}
