package com.trade.data.enricher.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO representing a Product.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  @CsvBindByPosition(position = 0, required = true)
  private String id;

  @CsvBindByPosition(position = 1, required = true)
  private String name;

}
