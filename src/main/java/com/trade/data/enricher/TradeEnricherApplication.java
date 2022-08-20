package com.trade.data.enricher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bootstrap class of the Spring Boot application
 */
@Slf4j
@SpringBootApplication
public class TradeEnricherApplication {

  public static void main(String[] args) {
    log.info("Trade Enricher Service starting up.");
    SpringApplication.run(TradeEnricherApplication.class, args);
    log.info("Trade Enricher Service started.");
  }

}
