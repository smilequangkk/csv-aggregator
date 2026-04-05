package com.company.app;

import java.io.File;

/**
 * Utility class responsible for validating input and output paths.
 */
public class FileValidator {

    /**
     * Validates the input CSV file and the output directory.
     *
     * @param inputPath  The path to the input CSV file.
     * @param outputPath The path to the output directory.
     * @throws IllegalArgumentException if any of the paths are invalid.
     */
    public static void validatePaths(String inputPath, String outputPath) {
        // 1. Validate Input File
        File inFile = new File(inputPath);
        if (!inFile.exists()) {
            throw new IllegalArgumentException("Input file does not exist at path: " + inFile.getAbsolutePath());
        }
        if (!inFile.isFile()) {
            throw new IllegalArgumentException("Input path is not a valid file: " + inFile.getAbsolutePath());
        }

        // 2. Validate Output Directory
        File outDir = new File(outputPath);
        if (outDir.exists()) {
            if (!outDir.isDirectory()) {
                throw new IllegalArgumentException("Output path already exists but is a file, not a directory: " + outDir.getAbsolutePath());
            }
        } else {
            System.out.println("Output directory does not exist. Creating directory: " + outDir.getAbsolutePath());
            boolean created = outDir.mkdirs();
            if (!created) {
                throw new IllegalArgumentException("Could not create output directory. Check your folder permissions.");
            }
        }
    }
}