package org.example.csv_merger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CSVMerger {

    private static final String STUDENTS1_FILE = "D://IdeaProjects//week5//csv advanced//src//main//java//org//example//students1.csv";
    private static final String STUDENTS2_FILE = "D://IdeaProjects//week5//csv advanced//src//main//java//org//example//students1.csv";
    private static final String MERGED_FILE = "D://IdeaProjects//week5//csv advanced//src//main//java//org//example//merged_students.csv";

    public static void main(String[] args) {
        Map<String, String[]> students1Data = new HashMap<>();
        Map<String, String[]> students2Data = new HashMap<>();

        // Read students1.csv
        try (BufferedReader br1 = new BufferedReader(new FileReader(STUDENTS1_FILE))) {
            String line;
            br1.readLine(); // Skip header
            while ((line = br1.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    students1Data.put(data[0].trim(), data); // ID is the key
                } else {
                    System.err.println("Skipping invalid row in " + STUDENTS1_FILE + ": " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Read students2.csv
        try (BufferedReader br2 = new BufferedReader(new FileReader(STUDENTS2_FILE))) {
            String line;
            br2.readLine(); // Skip header
            while ((line = br2.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    students2Data.put(data[0].trim(), data); // ID is the key
                } else {
                    System.err.println("Skipping invalid row in " + STUDENTS2_FILE + ": " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Merge and write to merged_students.csv
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MERGED_FILE))) {
            // Write header
            bw.write("ID,Name,Age,Marks,Grade\n");

            // Iterate through IDs from students1.csv (you could also iterate through the union of the keysets)
            for (String id : students1Data.keySet()) {
                String[] student1 = students1Data.get(id);
                String[] student2 = students2Data.get(id); // Try to get matching data from students2

                if (student2 != null) {
                    // Found a match, write merged data
                    bw.write(id + "," + student1[1].trim() + "," + student1[2].trim() + "," + student2[1].trim() + "," + student2[2].trim() + "\n");
                } else {
                    // No match in students2.csv, write data with empty Marks and Grade
                    bw.write(id + "," + student1[1].trim() + "," + student1[2].trim() + ",,\n");
                    System.err.println("Warning: No matching record found in " + STUDENTS2_FILE + " for ID: " + id);
                }
            }
            //Write the remaining students from students2
            for(String id: students2Data.keySet()){
                if(!students1Data.containsKey(id)){
                    String[] student2 = students2Data.get(id);
                    bw.write(id + ",," + student2[1].trim() + "," + student2[2].trim() + "\n");
                    System.err.println("Warning: No matching record found in " + STUDENTS1_FILE + " for ID: " + id);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Merged data written to " + MERGED_FILE);
    }
}
