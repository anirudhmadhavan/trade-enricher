package com.trade.data.enricher.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.trade.data.enricher.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CsvProductLoaderImplTest {

  @Autowired
  ProductLoader productLoader;

  @Autowired
  ProductRepository productRepository;

  @Test
  public void test_create_product_repository_from_sample_csv() {
    // Doing an integration test with sample CSV to ensure all the products are loaded successfully
    productLoader.loadProducts();

    for (int i = 1; i <= 10; i++) {
      assertTrue(productRepository.checkIfProductExists(String.valueOf(i)));
    }

    assertTrue(productRepository.getProductName("1").isPresent());
    assertEquals("Treasury Bills Domestic", productRepository.getProductName("1").get());
    assertTrue(productRepository.getProductName("3").isPresent());
    assertEquals("REPO Domestic", productRepository.getProductName("3").get());
    assertTrue(productRepository.getProductName("7").isPresent());
    assertEquals("Reverse Repos International", productRepository.getProductName("7").get());
    assertTrue(productRepository.getProductName("10").isPresent());
    assertEquals("766B_CORP BD", productRepository.getProductName("10").get());
    assertFalse(productRepository.getProductName("0").isPresent());
    assertFalse(productRepository.getProductName("11").isPresent());
  }

}
