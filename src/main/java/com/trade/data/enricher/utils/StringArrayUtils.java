package com.trade.data.enricher.utils;

import org.apache.commons.lang3.StringUtils;

public class StringArrayUtils {

  /**
   * Checks if input string array is valid. A string array is considered valid if: -> array is not
   * null -> array's length is not 0 -> any of the array's elements is not a null or blank string
   * when trimmed.
   *
   * @param s string array
   * @return true/false if array is valid or not
   */
  public static boolean isStringArrayNotBlank(String... s) {
    if (s == null || s.length == 0) {
      return false;
    }

    for (String test : s) {
      if (StringUtils.isBlank(test)) {
        return false;
      }
    }
    return true;
  }

}
