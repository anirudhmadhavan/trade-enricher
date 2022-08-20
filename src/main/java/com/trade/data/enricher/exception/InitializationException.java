package com.trade.data.enricher.exception;

/**
 * RuntimeException thrown if there is any error while starting the service.
 */
public class InitializationException extends RuntimeException {

  public InitializationException(String message, Throwable cause) {
    super(message, cause);
  }
}
