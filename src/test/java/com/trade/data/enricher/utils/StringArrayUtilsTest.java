package com.trade.data.enricher.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringArrayUtilsTest {

  @Test
  public void test_is_valid_string_array() {
    assertFalse(StringArrayUtils.isStringArrayNotBlank(null));
    assertFalse(StringArrayUtils.isStringArrayNotBlank());
    assertFalse(StringArrayUtils.isStringArrayNotBlank("1", null));
    assertFalse(StringArrayUtils.isStringArrayNotBlank("1", " "));
    assertFalse(StringArrayUtils.isStringArrayNotBlank("1", ""));
    assertFalse(StringArrayUtils.isStringArrayNotBlank(""));
    assertTrue(StringArrayUtils.isStringArrayNotBlank("1"));
    assertTrue(StringArrayUtils.isStringArrayNotBlank("1", "2"));
  }

}
