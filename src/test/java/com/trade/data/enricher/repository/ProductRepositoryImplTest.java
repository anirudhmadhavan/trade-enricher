package com.trade.data.enricher.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.trade.data.enricher.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductRepositoryImplTest {

  ProductRepository productRepository;

  @BeforeEach
  public void setup() {
    productRepository = new ProductRepositoryImpl();
  }

  @Test
  public void test_add_product() {
    String id = "100", name = "TEST";
    assertFalse(productRepository.checkIfProductExists(id));
    assertTrue(productRepository.getProductName(id).isEmpty());
    productRepository.addProduct(new Product(id, name));
    assertTrue(productRepository.checkIfProductExists(id));
    assertFalse(productRepository.getProductName(id).isEmpty());
    assertEquals(name, productRepository.getProductName(id).get());
  }

  @Test
  public void test_duplicate_add_product() {
    String id = "100", name1 = "TEST1", name2 = "TEST2";
    productRepository.addProduct(new Product(id, name1));
    assertTrue(productRepository.checkIfProductExists(id));
    assertFalse(productRepository.getProductName(id).isEmpty());
    assertEquals(name1, productRepository.getProductName(id).get());
    productRepository.addProduct(new Product(id, name2));
    assertTrue(productRepository.checkIfProductExists(id));
    assertFalse(productRepository.getProductName(id).isEmpty());
    assertEquals(name2, productRepository.getProductName(id).get());
  }

}
