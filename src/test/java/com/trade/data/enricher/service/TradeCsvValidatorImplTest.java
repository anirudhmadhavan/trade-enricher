package com.trade.data.enricher.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

class TradeCsvValidatorImplTest {

  TradeCsvValidator tradeCsvValidator;

  MultipartFile file;

  @BeforeEach
  public void setup() {
    tradeCsvValidator = new TradeCsvValidatorImpl();
    file = mock(MultipartFile.class);
  }

  @Test
  public void test_is_valid_csv_file() {
    Mockito.when(file.isEmpty()).thenReturn(false);
    Mockito.when(file.getContentType()).thenReturn(TradeCsvValidatorImpl.TYPES.get(0));
    assertTrue(tradeCsvValidator.validateCsv(file));
  }

  @Test
  public void test_is_empty_csv_file() {
    Mockito.when(file.isEmpty()).thenReturn(true);
    Mockito.when(file.getContentType()).thenReturn(TradeCsvValidatorImpl.TYPES.get(0));
    assertFalse(tradeCsvValidator.validateCsv(file));
  }

  @Test
  public void test_is_not_csv_file() {
    Mockito.when(file.isEmpty()).thenReturn(false);
    Mockito.when(file.getContentType()).thenReturn("txt");
    assertFalse(tradeCsvValidator.validateCsv(file));
  }

  @Test
  public void test_row_does_have_4_values() {
    assertFalse(tradeCsvValidator.validateRowInCsv(new String[]{"2201", "2", "3"}));
  }

  @Test
  public void test_row_has_4_values_and_is_valid() {
    assertTrue(tradeCsvValidator.validateRowInCsv(new String[]{"22010101", "2", "3", "2"}));
  }

}
