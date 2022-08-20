package com.trade.data.enricher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown whenever an invalid trade csv is provided as input.
 */
public class InvalidTradeCsvException extends ResponseStatusException {

  public InvalidTradeCsvException(String reason) {
    super(HttpStatus.BAD_REQUEST, reason);
  }

}
