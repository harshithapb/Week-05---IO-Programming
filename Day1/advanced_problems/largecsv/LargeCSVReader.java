package org.example.largecsv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LargeCSVReader {

    private static final String LARGE_CSV_FILE = "D://IdeaProjects//week5//csv advanced//src//main//java//org//example//largecsv//large_data.csv";
    private static final int CHUNK_SIZE = 100;

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(LARGE_CSV_FILE))) {
            String line;
            int totalRecordsProcessed = 0;
            int currentChunkSize = 0;

            // Skip the header row
            br.readLine();
            totalRecordsProcessed++; // Account for the header

            while ((line = br.readLine()) != null) {
                // Process the current line (in a real scenario, you'd do your data processing here)
                // System.out.println("Processing line: " + line);

                currentChunkSize++;
                totalRecordsProcessed++;

                if (currentChunkSize == CHUNK_SIZE) {
                    System.out.println("Processed " + currentChunkSize + " records in this chunk. Total processed: " + totalRecordsProcessed);
                    currentChunkSize = 0; // Reset chunk size for the next chunk
                }
            }

            // Process any remaining lines in the last chunk
            if (currentChunkSize > 0) {
                System.out.println("Processed remaining " + currentChunkSize + " records. Total processed: " + totalRecordsProcessed);
            }

            System.out.println("Finished processing the CSV file. Total records processed (excluding header): " + (totalRecordsProcessed - 1));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
