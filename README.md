# CSV Campaign Aggregator (Java CLI)

This project is a Java console application that processes a large CSV file (~1GB) containing advertising performance records and generates aggregated campaign analytics.

It outputs:
- **Top 10 campaigns with highest CTR**
- **Top 10 campaigns with lowest CPA**

---

## Requirements

- Java 21
- Maven 3.8+

---

## Setup Instructions

Clone the repository and build the project:

```bash
git clone https://github.com/smilequangkk/csv-aggregator.git
cd csv-aggregator
mvn clean package
```

---

## How to run

```bash
java -cp target/csv-aggregator-1.0-SNAPSHOT.jar com.company.app.Main --input <path-to-csv> --output <output-folder>
```

---


## Processing Time (1GB File)

Test Environment Example:

- CPU: Core i7-1260P
- RAM: 16GB
- OS: Window
- Storage: SSD

Processing time for the 1GB ad_data.csv file: ~2574ms