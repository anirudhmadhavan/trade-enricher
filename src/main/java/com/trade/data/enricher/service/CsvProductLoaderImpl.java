package com.trade.data.enricher.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.trade.data.enricher.exception.InitializationException;
import com.trade.data.enricher.model.Product;
import com.trade.data.enricher.repository.ProductRepository;
import java.io.InputStreamReader;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Implementation of @ProductLoader using CSV on classpath.
 */
@Service
public class CsvProductLoaderImpl implements
    ProductLoader {

  private final ProductRepository productRepository;
  private final String inputFilePath;

  public CsvProductLoaderImpl(ProductRepository productRepository,
      @Value("${application.product.file-path}") String inputFilePath) {
    this.productRepository = productRepository;
    this.inputFilePath = inputFilePath;
  }

  /**
   * Automatically called after bean creation on application startup. Loads products one by one in
   *
   * @ProductRepository. Throws @InitializationException if unable to load products cleanly.
   * <p>
   * Assumptions: First line of product CSV is header. Each line of product CSV is valid, has two
   * fields only - ID and name. ID and name can be represented as a string. Empty lines, quotations
   * and leading white spaces can be ignored in the CSV file. CSV file is present in
   * ${application.product.file-path} on the classpath. If two rows exist with the same product ID,
   * only the first is inserted into the repository.
   */
  @Override
  @PostConstruct
  public void loadProducts() {
    try {
      Resource resource = new ClassPathResource(inputFilePath);
      CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(
          new InputStreamReader(resource.getInputStream()))
          .withType(Product.class)
          .withIgnoreLeadingWhiteSpace(true)
          .withIgnoreQuotations(true)
          .withIgnoreEmptyLine(true)
          .withSkipLines(1)
          .build();

      csvToBean.stream()
          // Ignoring multiple entries with same product ID, only the first is considered
          .filter(product -> !productRepository.checkIfProductExists(product.getId()))
          .forEach(productRepository::addProduct);

    } catch (Exception e) {
      throw new InitializationException("Unable to parse CSV file to create products list", e);
    }
  }

}
