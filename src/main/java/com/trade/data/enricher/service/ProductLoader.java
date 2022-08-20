package com.trade.data.enricher.service;

/**
 * Service to load products in @ProductRepository.
 */
public interface ProductLoader {

  /**
   * Load all products in @ProductRepository.
   */
  void loadProducts();

}
