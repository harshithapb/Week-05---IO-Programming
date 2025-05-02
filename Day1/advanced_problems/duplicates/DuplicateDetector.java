package org.example.duplicates;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DuplicateDetector {

    private static final String INPUT_FILE = "D://IdeaProjects//week5//csv advanced//src//main//java//org//example//duplicates//duplicates.csv";

    public static void main(String[] args) {
        Set<String> uniqueIds = new HashSet<>();
        Set<String> duplicateRecords = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    String id = data[0].trim();
                    if (!uniqueIds.add(id)) {
                        duplicateRecords.add(line);
                    }
                } else {
                    System.err.println("Skipping empty line.");
                }
            }

            if (!duplicateRecords.isEmpty()) {
                System.out.println("--- Duplicate Records (based on ID) ---");
                for (String record : duplicateRecords) {
                    System.out.println(record);
                }
            } else {
                System.out.println("No duplicate records found based on ID.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
