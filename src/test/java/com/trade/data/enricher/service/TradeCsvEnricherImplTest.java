package com.trade.data.enricher.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.opencsv.exceptions.CsvValidationException;
import com.trade.data.enricher.model.Product;
import com.trade.data.enricher.repository.ProductRepository;
import com.trade.data.enricher.repository.ProductRepositoryImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TradeCsvEnricherImplTest {

  private TradeCsvEnricher tradeCsvEnricher;

  @BeforeEach
  public void setUp() {
    ProductRepository productRepository = new ProductRepositoryImpl();
    TradeCsvValidator tradeCsvValidator = new TradeCsvValidatorImpl();
    productRepository.addProduct(new Product("1", "TEST1"));
    productRepository.addProduct(new Product("2", "TEST2"));
    tradeCsvEnricher = new TradeCsvEnricherImpl(tradeCsvValidator, productRepository);
  }

  @Test
  public void test_valid_rows_are_mapped() throws CsvValidationException, IOException {

    // Row 3 is invalid
    // Row 4 and 5 do not have product name according to setup above
    String inputString = "date,product_id,currency,price\n"
        + "20160101,1,EUR,10.0\n"
        + "20160101,2,EUR,20.1\n"
        + "201601,2,EUR,20.1\n"
        + "20160101,3,EUR,30.34\n"
        + "20160101,11,EUR,35.34";

    String expectedString = "date,product_name,currency,price\n"
        + "20160101,TEST1,EUR,10.0\n"
        + "20160101,TEST2,EUR,20.1\n"
        + "20160101,Missing Product Name,EUR,30.34\n"
        + "20160101,Missing Product Name,EUR,35.34\n";

    InputStream in = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    tradeCsvEnricher.enrichCsv(in, out);

    assertEquals(out.toString(), expectedString);
  }

  @Test
  public void test_all_rows_are_invalid() throws CsvValidationException, IOException {

    String inputString = "date,product_id,currency,price\n"
        + "110101,1,EUR,10.0\n"
        + ",2,EUR,20.1\n"
        + "2160101,11,EUR,35.34";

    String expectedString = "date,product_name,currency,price\n";

    InputStream in = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    tradeCsvEnricher.enrichCsv(in, out);

    assertEquals(out.toString(), expectedString);
  }

}
