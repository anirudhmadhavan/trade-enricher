package com.trade.data.enricher.repository;

import com.trade.data.enricher.model.Product;
import java.util.Optional;

/**
 * Repository layer to hold products and provide helper functions to query them.
 */
public interface ProductRepository {

  /**
   * Adds a new product into the repository.
   *
   * @param product Product object
   */
  void addProduct(Product product);

  /**
   * Check if product with id exists in the repository.
   *
   * @param id ID of the product to be checked
   * @return true/false if product exists
   */
  boolean checkIfProductExists(String id);

  /**
   * Get the product name of product with id.
   *
   * @param id ID of the product for which name is to be retrieved
   * @return Name of the product, if it exists or empty
   */
  Optional<String> getProductName(String id);

}
