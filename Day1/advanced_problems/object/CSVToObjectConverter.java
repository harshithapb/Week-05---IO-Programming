package org.example.object;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVToObjectConverter {

    private static final String INPUT_FILE = "D://IdeaProjects//week5//csv advanced//src//main//java//org//example//students.csv"; // Relative path within your project

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String header = br.readLine(); // Skip the header row (if it exists)

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 4) { // Assuming your CSV has ID, Name, Department, Salary
                    try {
                        String id = data[0].trim();
                        String name = data[1].trim();
                        String department = data[2].trim();
                        double salary = Double.parseDouble(data[3].trim());
                        Student student = new Student(id, name, department, salary);
                        students.add(student);
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Invalid salary format in line - " + line);
                    }
                } else {
                    System.err.println("Warning: Incorrect number of columns in line - " + line);
                }
            }

            // Print the list of Student objects
            System.out.println("--- List of Student Objects ---");
            for (Student student : students) {
                System.out.println(student); // toString() method will be used
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}