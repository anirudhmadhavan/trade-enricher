package com.trade.data.enricher.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.trade.data.enricher.repository.ProductRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of @TradeCsvEnricher using @ProductRepository @TradeCsvValidator.
 */
@Slf4j
@Service
public class TradeCsvEnricherImpl implements
    TradeCsvEnricher {

  // Constant representing product name to be returned when it doesn't exist
  public static final String MISSING_PRODUCT_NAME = "Missing Product Name";
  // Headers for output CSV file
  private final String[] headers = new String[]{"date", "product_name", "currency", "price"};
  private final TradeCsvValidator tradeCsvValidator;
  private final ProductRepository productRepository;

  public TradeCsvEnricherImpl(TradeCsvValidator tradeCsvValidator,
      ProductRepository productRepository) {
    this.tradeCsvValidator = tradeCsvValidator;
    this.productRepository = productRepository;
  }

  /**
   * Streams the input CSV, processes line by line and writes the enriched line to output stream.
   * <p>
   * Assumptions: First line of input CSV is always a header. If any of the rows are not valid
   * (date, values, null), then that row is skipped. If all the rows are invalid, then output csv
   * will just have header. Output CSV does not have quotes and is delimited by comma. If product
   * name is missing, we use MISSING_PRODUCT_NAME.
   */

  @Override
  public void enrichCsv(InputStream in, OutputStream out)
      throws IOException, CsvValidationException {

    try (CSVReader csvReader = new CSVReader(
        new BufferedReader(new InputStreamReader(in))); ICSVWriter writer = new CSVWriterBuilder(
        new OutputStreamWriter(out)).withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER).build()) {

      String[] nextRecord;
      int row = 0;
      while ((nextRecord = csvReader.readNext()) != null) {
        if (row == 0) {
          // Handle header
          writer.writeNext(headers);
        } else {
          if (tradeCsvValidator.validateRowInCsv(nextRecord)) {
            nextRecord[1] = productRepository.getProductName(nextRecord[1])
                .orElse(MISSING_PRODUCT_NAME);
            writer.writeNext(nextRecord);
          } else {
            log.warn(
                "Invalid row {}. Row or some value in row is empty or date format is invalid = {}",
                row, Arrays.toString(nextRecord));
          }
        }
        row++;
      }
    }
  }

}
