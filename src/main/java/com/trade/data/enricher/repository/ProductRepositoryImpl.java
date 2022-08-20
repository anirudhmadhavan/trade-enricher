package com.trade.data.enricher.repository;

import com.trade.data.enricher.model.Product;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * Implementation of @ProductRepository using an in memory map.
 */
@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepository {

  /**
   * Storing the list of products in memory in map Map of ID - product object.
   */
  private final Map<String, Product> productMap;

  public ProductRepositoryImpl() {
    productMap = new HashMap<>();
  }

  @Override
  public void addProduct(Product product) {
    log.info("Adding product {}, {}", product.getId(), product.getName());
    productMap.put(product.getId(), product);
  }

  @Override
  public boolean checkIfProductExists(String id) {
    return productMap.containsKey(id);
  }

  @Override
  public Optional<String> getProductName(String id) {
    if (checkIfProductExists(id)) {
      return Optional.of(productMap.get(id).getName());
    } else {
      return Optional.empty();
    }
  }

}
