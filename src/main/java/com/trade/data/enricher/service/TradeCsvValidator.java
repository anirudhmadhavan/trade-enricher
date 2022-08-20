package com.trade.data.enricher.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service used to validate incoming trade CSV file.
 */
public interface TradeCsvValidator {

  /**
   * Validate incoming file.
   *
   * @param file input file
   * @return true/false if file is valid or not
   */
  boolean validateCsv(MultipartFile file);

  /**
   * Validate a row in the incoming file.
   *
   * @param row row in the input file
   * @return true/false if row is valid or not
   */
  boolean validateRowInCsv(String[] row);

}
