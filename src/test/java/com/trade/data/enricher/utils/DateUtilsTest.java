package com.trade.data.enricher.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DateUtilsTest {

  @Test
  public void test_is_valid_date() {
    assertTrue(DateUtils.isValidDate("20200101"));
    assertTrue(DateUtils.isValidDate("20210503"));
    assertFalse(DateUtils.isValidDate("2021-05-03"));
    assertFalse(DateUtils.isValidDate("210101"));
    assertFalse(DateUtils.isValidDate(""));
    assertFalse(DateUtils.isValidDate("String"));
    assertFalse(DateUtils.isValidDate("202111"));
  }

}
