package com.company.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

/**
 * Handles concurrent CSV parsing and aggregation.
 */
public class CsvProcessor {

    // Schema details
    private static final int CAMPAIGN_ID_INDEX = 0;
    private static final int IMPRESSIONS_INDEX = 2;
    private static final int CLICKS_INDEX = 3;
    private static final int SPEND_INDEX = 4;
    private static final int CONVERSIONS_INDEX = 5;

    /**
     * Efficiently processes a large CSV file using parallel streams and concurrent data structures.
     *
     * @param filePath The path to the input CSV file.
     * @return A thread-safe Map containing aggregated CampaignData.
     * @throws IOException If a fatal I/O error occurs.
     */
    public static Map<String, CampaignData> processCsv(String filePath) throws IOException {
        // High-performance concurrent map
        ConcurrentHashMap<String, CampaignData> campaignMap = new ConcurrentHashMap<>();

        LongAdder totalRows = new LongAdder();
        LongAdder skippedRows = new LongAdder();

        System.out.println("Initializing parallel file processing stream...");

        // Files.lines reads lazily, stream.parallel() parallelizes the processing
        try (Stream<String> lines = Files.lines(Paths.get(filePath)).parallel()) {
            lines.forEach(line -> {
                totalRows.increment();

                // Fast header detection
                if (line.startsWith("campaign_id")) {
                    return; // Skip header
                }

                // Basic validation: must have at least one character to be a line
                if (line.isEmpty()) {
                    return;
                }

                String[] parts = line.split(",");
                // Ensure the row has the expected number of columns (6)
                if (parts.length < 6) {
                    skippedRows.increment();
                    return;
                }

                try {
                    String campaignId = parts[CAMPAIGN_ID_INDEX];
                    long impressions = Long.parseLong(parts[IMPRESSIONS_INDEX]);
                    long clicks = Long.parseLong(parts[CLICKS_INDEX]);
                    double spend = Double.parseDouble(parts[SPEND_INDEX]);
                    long conversions = Long.parseLong(parts[CONVERSIONS_INDEX]);

                    // computeIfAbsent is thread-safe
                    campaignMap.computeIfAbsent(campaignId, CampaignData::new)
                            .addRecord(impressions, clicks, spend, conversions);

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // Silently ignore malformed numerical data or unexpected column counts
                    skippedRows.increment();
                }
            });
        } catch (Exception e) {
            // Rethrow or wrap fatal exceptions
            throw new IOException("Critical error during parallel CSV parsing: " + e.getMessage(), e);
        }

        // Final summary log (single execution, no concurrency needed)
        System.out.println("Parallel reading and aggregation completed.");
        System.out.println(String.format("Processed %,d total rows. Skipped %,d invalid/malformed rows.", totalRows.sum(), skippedRows.sum()));
        System.out.println("Found " + campaignMap.size() + " unique campaign IDs.");

        return campaignMap;
    }
}