package com.trade.data.enricher.controller;

import com.opencsv.exceptions.CsvValidationException;
import com.trade.data.enricher.exception.InvalidTradeCsvException;
import com.trade.data.enricher.exception.TradeCsvProcessingException;
import com.trade.data.enricher.service.TradeCsvEnricher;
import com.trade.data.enricher.service.TradeCsvValidator;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * Rest controller exposing endpoint for handling Trade CSV files.
 */
@Slf4j
@RestController
public class TradeCsvController {

  private final TradeCsvValidator tradeCsvValidator;
  private final TradeCsvEnricher tradeCsvEnricher;

  public TradeCsvController(TradeCsvValidator tradeCsvValidator,
      TradeCsvEnricher tradeCsvEnricher) {
    this.tradeCsvValidator = tradeCsvValidator;
    this.tradeCsvEnricher = tradeCsvEnricher;
  }

  /**
   * API to enrich trade CSV with product information. Streams response back to the client so
   * application can directly write to output stream in chunks.
   *
   * @param file request parameter which is a non empty CSV file
   * @return streaming response of output CSV file
   */
  @PostMapping(value = "/v1/enrich")
  public ResponseEntity<StreamingResponseBody> enrichTrade(
      @RequestParam("file") MultipartFile file) {

    if (!tradeCsvValidator.validateCsv(file)) {
      throw new InvalidTradeCsvException("Input should be a valid non empty CSV file");
    }

    StreamingResponseBody body = out -> {
      try {
        tradeCsvEnricher.enrichCsv(file.getInputStream(), out);
      } catch (IOException | CsvValidationException e) {
        log.error("Error while enriching trade csv", e);
        throw new TradeCsvProcessingException("Error while enriching trade csv", e);
      }
    };

    return ResponseEntity.ok()
        .headers(getHttpHeaders())
        .body(body);
  }

  private HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType("text/csv"));
    headers.setContentDisposition(
        ContentDisposition.builder("attachment")
            .filename("trades_enriched.csv")
            .build());
    return headers;
  }

}
