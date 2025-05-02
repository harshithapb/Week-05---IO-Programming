package org.example.validate;

// OpenCSVValidationExample.java
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateCSV {

    private static final String INPUT_FILE = "D:/IdeaProjects/week5/csv advanced/src/main/java/org/example/contacts.csv";
    private static final int EMAIL_COLUMN_INDEX = 2; // Assuming Email is the 3rd column (0-based)
    private static final int PHONE_COLUMN_INDEX = 3; // Assuming Phone Number is the 4th column (0-based)

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static void validateCSVData() {
        try (CSVReader reader = new CSVReader(new FileReader(INPUT_FILE))) {
            String[] header = reader.readNext(); // Read the header row (optional, but good to skip)
            if (header != null) {
                System.out.println("Processing CSV file: " + INPUT_FILE);
                System.out.println("----------------------------------");
            }

            String[] row;
            int rowNumber = 1;
            while ((row = reader.readNext()) != null) {
                boolean isValidRow = true;

                // Validate Email
                if (row.length > EMAIL_COLUMN_INDEX) {
                    String email = row[EMAIL_COLUMN_INDEX].trim();
                    Matcher matcher = EMAIL_PATTERN.matcher(email);
                    if (!matcher.matches()) {
                        System.err.println("Error in Row " + rowNumber + ": Invalid Email format - " + email);
                        isValidRow = false;
                    }
                } else {
                    System.err.println("Error in Row " + rowNumber + ": Missing Email column.");
                    isValidRow = false;
                }

                // Validate Phone Number
                if (row.length > PHONE_COLUMN_INDEX) {
                    String phoneNumber = row[PHONE_COLUMN_INDEX].trim();
                    if (!phoneNumber.matches("\\d{10}")) {
                        System.err.println("Error in Row " + rowNumber + ": Invalid Phone Number - " + phoneNumber + " (must be 10 digits)");
                        isValidRow = false;
                    }
                } else {
                    System.err.println("Error in Row " + rowNumber + ": Missing Phone Number column.");
                    isValidRow = false;
                }

                if (isValidRow) {
                    // Process the valid row here
                    System.out.println("Row " + rowNumber + " is valid: " + String.join(",", row));
                }

                rowNumber++;
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        validateCSVData();
    }
}