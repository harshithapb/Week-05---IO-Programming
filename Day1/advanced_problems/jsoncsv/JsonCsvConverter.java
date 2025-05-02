package org.example.jsoncsv;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonCsvConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void convertJsonToCsv(String jsonFilePath, String csvFilePath) {
        try {
            List<Student> students = objectMapper.readValue(new File(jsonFilePath), new TypeReference<List<Student>>() {});
            if (!students.isEmpty()) {
                try (FileWriter writer = new FileWriter(csvFilePath)) {
                    // Write header
                    writer.write(String.join(",", "ID", "Name", "Department", "Salary") + "\n");

                    // Write data rows
                    for (Student student : students) {
                        writer.write(String.join(",", student.getId(), student.getName(), student.getDepartment(), String.valueOf(student.getSalary())) + "\n");
                    }
                    System.out.println("JSON converted to CSV and saved at: " + csvFilePath);
                } catch (IOException e) {
                    System.err.println("Error writing to CSV file: " + e.getMessage());
                }
            } else {
                System.out.println("JSON file is empty, no CSV created.");
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
    }

    public static void convertCsvToJson(String csvFilePath, String jsonFilePath) {
        List<Student> students = null;
        try (Stream<String> lines = Files.lines(Paths.get(csvFilePath))) {
            List<String> lineList = lines.collect(Collectors.toList());
            if (lineList.size() > 1) {
                String header = lineList.get(0);
                List<String> dataLines = lineList.subList(1, lineList.size());
                students = dataLines.stream()
                        .map(line -> {
                            String[] data = line.split(",");
                            if (data.length == 4) {
                                try {
                                    return new Student(data[0].trim(), data[1].trim(), data[2].trim(), Double.parseDouble(data[3].trim()));
                                } catch (NumberFormatException e) {
                                    System.err.println("Warning: Invalid salary format in CSV - " + line);
                                    return null;
                                }
                            } else {
                                System.err.println("Warning: Incorrect number of columns in CSV - " + line);
                                return null;
                            }
                        })
                        .filter(student -> student != null)
                        .collect(Collectors.toList());

                objectMapper.writeValue(new File(jsonFilePath), students);
                System.out.println("CSV converted to JSON and saved at: " + jsonFilePath);

            } else {
                System.out.println("CSV file is empty or contains only header, no JSON created.");
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String jsonInputFile = "data/students.json";
        String csvOutputFile = "data/students.csv";
        String jsonOutputFile = "data/students_from_csv.json";

        // Create a sample JSON file
        List<Student> sampleStudents = List.of(
                new Student("S101", "Alice", "Science", 80000.00),
                new Student("S102", "Bob", "Math", 75000.50),
                new Student("S103", "Charlie", "Engineering", 90000.25)
        );
        try {
            objectMapper.writeValue(new File(jsonInputFile), sampleStudents);
            System.out.println("Sample JSON file created at: " + jsonInputFile);
        } catch (IOException e) {
            System.err.println("Error creating sample JSON: " + e.getMessage());
        }

        // Convert JSON to CSV
        convertJsonToCsv(jsonInputFile, csvOutputFile);

        // Convert CSV back to JSON
        convertCsvToJson(csvOutputFile, jsonOutputFile);
    }
}