package com.trade.data.enricher.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TradeCsvControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Value("${application.trades.file-path}")
  private String filePath;

  @Test
  public void test_valid_trades_file_uploaded()
      throws Exception {

    MockMultipartFile file
        = new MockMultipartFile(
        "file",
        "sample_trades.csv",
        "text/csv",
        new ClassPathResource(filePath).getInputStream()
    );

    String expectedString = "date,product_name,currency,price\n"
        + "20160101,Treasury Bills Domestic,EUR,10.0\n"
        + "20160101,Corporate Bonds Domestic,EUR,20.1\n"
        + "20160101,REPO Domestic,EUR,30.34\n"
        + "20160101,Missing Product Name,EUR,35.34\n";

    // Using just /v1/enrich since this is a mock servlet environment
    // Only when servlet container starts the prefix /api is appended
    mockMvc.perform(multipart("/v1/enrich").file(file))
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/csv"))
        .andExpect(content().string(expectedString));
  }

  @Test
  public void test_empty_trades_file_uploaded()
      throws Exception {

    MockMultipartFile file
        = new MockMultipartFile(
        "file",
        "sample_trades.csv",
        "text/csv",
        "".getBytes(StandardCharsets.UTF_8)
    );

    mockMvc.perform(multipart("/v1/enrich").file(file))
        .andExpect(status().isBadRequest());
  }

}
