package com.company.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Handles output operations.
 */
public class CsvWriter {

    /**
     * Writes a list of CampaignData objects to a CSV report file.
     *
     * @param campaigns The list of filtered and sorted campaigns.
     * @param filePath The destination file path.
     * @throws IOException If a fatal I/O error occurs.
     */
    public static void writeReport(List<CampaignData> campaigns, String filePath) throws IOException {
        File file = new File(filePath);

        // Ensure parent directory exists
        if (file.getParentFile() != null && file.getParentFile().mkdirs()) {
            System.out.println("Created missing output directory: " + file.getParentFile().getAbsolutePath());
        }

        System.out.println("Generating report file: " + filePath);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("campaign_id,total_impressions,total_clicks,total_spend,total_conversions,CTR,CPA\n");
            for (CampaignData data : campaigns) {
                bw.write(data.toCsvRow() + "\n");
            }
        } catch (IOException e) {
            throw new IOException("Critical error writing to file " + filePath + ": " + e.getMessage(), e);
        }

        System.out.println("Successfully generated report with " + campaigns.size() + " records at: " + filePath);
    }
}
