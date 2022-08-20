Trade Enricher Service
====================

Repo Information
---------

### Configuration Properties

Products CSV File Path: `application.product.file-path=static/product.csv`

Application can be further configured to limit size of multipart files passed in.

### Assumptions

1. First line of both products CSV and trades CSV is the header.
2. Each line of the products CSV is valid and has two fields only - ID and name.
3. Product ID and name can be represented as a string.
4. If two rows exist with the same product ID, only the first is considered.
5. If product name is missing, MISSING_PRODUCT_NAME is used.
6. Valid trades CSV file is not empty and has valid content type.
7. Rows in trades CSV with null, empty rows or rows which does not have exactly 4 non empty values
   are invalid.
8. Valid Date format is yyyyMMdd.
9. If any of the rows in trade CSV are not valid (date, values, null), then that row is skipped.

### Technology Used

1. Java Open JDK 17 + Maven + Spring Boot 2.7.2
2. Open CSV 5.3

Running
---------------------
Tested on Open JDK 17.

### Using Maven and source code - during development

	./mvnw spring-boot:run  

### Using Java command and JAR file - during deployment

    ./mvnw clean package spring-boot:repackage

	java -jar target/enricher-1.0.0-RELEASE.jar

This automatically starts an embedded server on port 8080.

# API and Sample Output

`/api/v1/enrich` API

Example CURL:

```
curl --location --request POST 'localhost:8080/api/v1/enrich' --form 'file=@"/Code/enricher/src/main/resources/static/sample_trades.csv"'
```

Example CSV response with file name `trades_enriched.csv`:

```
date,product_name,currency,price
20160101,Treasury Bills Domestic,EUR,10.0
20160101,Corporate Bonds Domestic,EUR,20.1
20160101,REPO Domestic,EUR,30.34
20160101,Missing Product Name,EUR,35.34
```
