package com.trade.data.enricher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown whenever we are unable to process input trade CSV.
 */
public class TradeCsvProcessingException extends ResponseStatusException {

  public TradeCsvProcessingException(String reason, Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
  }

}
