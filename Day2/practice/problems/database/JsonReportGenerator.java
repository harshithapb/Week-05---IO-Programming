package practice.problems.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonReportGenerator {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CSV_JSON";
    private static final String DB_USER = "root"; // Replace with your MySQL username
    private static final String DB_PASSWORD = "root"; // Replace with your MySQL password

    public static void main(String[] args) {
        List<Map<String, Object>> records = fetchEmployeeData();
        if (records != null) {
            generateJsonReport(records, "employee_report.json");
        }
    }

    public static List<Map<String, Object>> fetchEmployeeData() {
        List<Map<String, Object>> employees = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, age, salary, hire_date FROM employees")) {

            while (rs.next()) {
                Map<String, Object> employee = new HashMap<>();
                employee.put("id", rs.getInt("id"));
                employee.put("name", rs.getString("name"));
                employee.put("age", rs.getInt("age"));
                employee.put("salary", rs.getDouble("salary"));
                Date hireDateSql = rs.getDate("hire_date");
                employee.put("hire_date", hireDateSql != null ? hireDateSql.toLocalDate() : null);
                employees.add(employee);
            }
            return employees;

        } catch (SQLException e) {
            System.err.println("Error fetching data: " + e.getMessage());
            return null;
        }
    }

    public static void generateJsonReport(List<Map<String, Object>> data, String outputFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule()); // To handle LocalDate

        try {
            objectMapper.writeValue(new File(outputFilePath), data);
            System.out.println("JSON report generated successfully: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error generating JSON report: " + e.getMessage());
        }
    }
}
