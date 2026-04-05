# Prompt 1

Write a Java 21 console application (CLI) to process a CSV file of ~1GB with the following requirements.

1. Input
- CSV file name: ad_data.csv
- Size: ~1GB
- CSV schema: campaign_id,date,impressions,clicks,spend,conversions
- Example:
```bash
     campaign_id,date,impressions,clicks,spend,conversions
     CMP001,2025-01-01,12000,300,45.50,12
     CMP002,2025-01-01,8000,120,28.00,4
```

2. Output requirements

Aggregate by campaign_id:
- total_impressions
- total_clicks
- total_spend
- total_conversions
- CTR = total_clicks / total_impressions
- CPA = total_spend / total_conversions
- If conversions = 0, ignore or return null for CPA

Then output 2 CSV files:Sau đó xuất ra 2 file CSV:

- A. top10_ctr.csv – Top 10 campaigns with highest CTR
- B. top10_cpa.csv – Top 10 campaigns with lowest CPA (exclude conversions = 0)

Output format:
```bash
campaign_id,total_impressions,total_clicks,total_spend,total_conversions,CTR,CPA
CMP042,	125000,	6250,	12500.50,	625,	0.0500,	20.00
CMP015,	340000,	15300,	30600.25,	1530,	0.0450,	20.00
```

---

# Prompt 2: Refactor the code into multiple classes instead of putting all logic in one file.

---

# Prompt 3: Add validation: check that the input file exists and create the output folder if it does not exist. Validate data in csv file

---

# Prompt 4: Implement logging in the program

---

# Prompt 5: Compare using a CSV library (e.g., Apache Commons CSV) versus standard Java IO in terms of performance.

---

# Prompt 6: Use multiple threads to improve performance