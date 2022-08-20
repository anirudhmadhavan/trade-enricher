package com.trade.data.enricher.service;

import com.trade.data.enricher.utils.DateUtils;
import com.trade.data.enricher.utils.StringArrayUtils;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementation of @TradeCsvValidator.
 */
@Service
public class TradeCsvValidatorImpl implements
    TradeCsvValidator {

  public static final List<String> TYPES = List.of("text/csv", "application/octet-stream");

  /**
   * A file is considered valid if: -> file is not empty -> file's format is text/csv or
   * application/octet-stream.
   * <p>
   * Can add a condition here to validate file size, however, defaulting to Spring default now.
   */
  @Override
  public boolean validateCsv(MultipartFile file) {
    return !file.isEmpty() && TYPES.contains(file.getContentType());
  }

  /**
   * A row is considered valid if: -> string array is valid according to @StringArrayUtils -> there
   * are 4 values in each row -> date is in the right format according to @DateUtils.
   * <p>
   * Not checking the data type of each individual value.
   */
  @Override
  public boolean validateRowInCsv(String[] row) {
    return StringArrayUtils.isStringArrayNotBlank(row) && row.length == 4 && DateUtils
        .isValidDate(row[0]);
  }

}
