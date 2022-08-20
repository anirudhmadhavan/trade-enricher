package com.trade.data.enricher.service;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Service to enrich incoming trade CSV file with product information.
 */
public interface TradeCsvEnricher {

  /**
   * Enrich input stream and write output to output stream.
   *
   * @param in  input stream representing csv file
   * @param out output stream to which output has to be written to
   * @throws IOException            io exception
   * @throws CsvValidationException csv validation exception
   */
  void enrichCsv(InputStream in, OutputStream out) throws IOException, CsvValidationException;

}
