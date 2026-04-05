package com.company.app;

import java.util.List;
import java.util.Map;

/**
 * Entry point for the Ad Data Aggregator CLI application.
 */
public class Main {

    public static void main(String[] args) {
        // Variable to hold parsed input arguments
        String inputFile = null;
        String outputDir = null;

        // Parse command line arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--input") && i + 1 < args.length) {
                inputFile = args[i + 1];
            } else if (args[i].equals("--output") && i + 1 < args.length) {
                outputDir = args[i + 1];
            }
        }

        // Validate basic arguments
        try {
            FileValidator.validatePaths(inputFile, outputDir);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Application initialized.");
        long startTime = System.currentTimeMillis();

        try {
            // Task 1: High-Performance Concurrent Processing
            System.out.println("======================");
            System.out.println("Step 1: Commencing parallel processing of CSV file: " + inputFile);
            Map<String, CampaignData> dataMap = CsvProcessor.processCsv(inputFile);

            // Task 2: Advanced Analysis with PriorityQueues
            System.out.println("======================");
            System.out.println("Step 2: Performing complex analytical tasks (Top 10 CTR/CPA)...");
            List<CampaignData> topCtr = CampaignAnalyzer.getTop10Ctr(dataMap.values());
            List<CampaignData> topCpa = CampaignAnalyzer.getTop10Cpa(dataMap.values());

            // Task 3: Output Generation
            System.out.println("======================");
            System.out.println("Step 3: Exporting data and creating reports in " + outputDir);
            CsvWriter.writeReport(topCtr, outputDir + "/top10_ctr.csv");
            CsvWriter.writeReport(topCpa, outputDir + "/top10_cpa.csv");

            long endTime = System.currentTimeMillis();
            System.out.println("======================");
            System.out.println("Execution successful. All tasks completed in " + (endTime - startTime) + "ms.");

        } catch (Exception e) {
            System.out.println("An unexpected severe error occurred, terminating: "+  e.getMessage());
            System.exit(2);
        }
    }

}