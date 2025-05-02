package org.example.encryption;

import org.example.encryption.DataEncryptor; // Assuming DataEncryptor is in this package
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.example.encryption.Student;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EncryptedCsvHandler {

    private static final String CSV_FILE = "D://IdeaProjects//week5//csv advanced//src//main//java//org//example//encryption//encrypted_students.csv";

    public static void writeEncryptedCsv(List<Student> students) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE))) {
            // Write header
            String[] header = {"ID", "Name", "Department", "Encrypted Salary", "Encrypted Email"};
            writer.writeNext(header);

            // Write data rows with encryption
            for (Student student : students) {
                String encryptedSalary = DataEncryptor.encrypt(String.valueOf(student.getSalary()));
                String encryptedEmail = DataEncryptor.encrypt(student.getEmail()); // Assuming Student has getEmail()
                String[] data = {student.getId(), student.getName(), student.getDepartment(), encryptedSalary, encryptedEmail};
                writer.writeNext(data);
            }
            System.out.println("Data written to " + CSV_FILE + " with encryption.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> readEncryptedCsv() {
        List<Student> students = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE))) {
            String[] header = reader.readNext(); // Read header
            if (header == null || header.length != 5) {
                System.err.println("Invalid header in CSV file.");
                return students;
            }

            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length == 5) {
                    String id = line[0];
                    String name = line[1];
                    String department = line[2];
                    String encryptedSalary = line[3];
                    String encryptedEmail = line[4];

                    String decryptedSalary = DataEncryptor.decrypt(encryptedSalary);
                    String decryptedEmail = DataEncryptor.decrypt(encryptedEmail);

                    try {
                        double salary = Double.parseDouble(decryptedSalary);
                        Student student = new Student(id, name, department, salary);
                        student.setEmail(decryptedEmail); // Assuming Student has setEmail()
                        students.add(student);
                    } catch (NumberFormatException e) {
                        System.err.println("Error decrypting or parsing salary for ID: " + id);
                    }
                } else {
                    System.err.println("Invalid number of columns in CSV row.");
                }
            }
            System.out.println("Data read from " + CSV_FILE + " with decryption.");
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static void main(String[] args) {
        // Create a sample list of students with email
        List<Student> studentsToWrite = List.of(
                new Student("E101", "Ella", "Physics", 90000.00),
                new Student("E102", "Finn", "Chemistry", 85000.50),
                new Student("E103", "Gina", "Biology", 78000.75)
        );
        studentsToWrite.forEach(s -> s.setEmail(s.getName().toLowerCase() + "@example.com")); // Set sample emails

        // Write encrypted data to CSV
        writeEncryptedCsv(studentsToWrite);

        // Read and decrypt data from CSV
        List<Student> readStudents = readEncryptedCsv();
        readStudents.forEach(System.out::println);
    }
}