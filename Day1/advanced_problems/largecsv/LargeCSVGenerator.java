package org.example.largecsv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LargeCSVGenerator {

    private static final String LARGE_CSV_FILE = "D://IdeaProjects//week5//csv advanced//src//main//java//org//example//largecsv//large_data.csv";
    private static final int NUM_ROWS = 10000; // Simulate a large file with 10,000 rows

    public static void main(String[] args) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LARGE_CSV_FILE))) {
            // Write header
            bw.write("ID,Name,Value1,Value2\n");

            // Write data rows
            for (int i = 1; i <= NUM_ROWS; i++) {
                bw.write(i + ",Data" + i + "," + Math.random() + "," + Math.random() + "\n");
            }
            System.out.println("Sample large CSV file generated: " + LARGE_CSV_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
