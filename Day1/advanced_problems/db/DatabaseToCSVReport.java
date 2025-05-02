package org.example.db;

import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class DatabaseToCSVReport {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/csvDB";
    private static final String DB_USER = "";//username
    private static final String DB_PASSWORD = "";//pwd
    private static final String CSV_FILE_PATH = "D://IdeaProjects//week5//csv advanced//src//main//java//org//example//db//employee_report.csv";
    private static final String QUERY = "SELECT employee_id, name, department, salary FROM employees";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY);
             BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {

            // Write headers
            bw.write("Employee ID,Name,Department,Salary\n");

            // Write data rows
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employee_id");
                String name = resultSet.getString("name");
                String department = resultSet.getString("department");
                double salary = resultSet.getDouble("salary");

                String line = employeeId + "," + name + "," + department + "," + salary + "\n";
                bw.write(line);
            }

            System.out.println("Employee report generated successfully at: " + CSV_FILE_PATH);

        } catch (SQLException e) {
            System.err.println("Error connecting to the database or executing the query: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
