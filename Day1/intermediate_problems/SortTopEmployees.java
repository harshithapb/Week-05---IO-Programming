package intermediate_problems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortTopEmployees {

    private static final String INPUT_FILE = "D://IdeaProjects//week5//CSV File Handling//src//intermediate_problems//employees.csv";
    private static final int TOP_N = 5;

    public static void findTopEmployeesBufferedReader() {
        List<String[]> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String header = br.readLine(); // Skip the header
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                if (cols.length >= 4) { // Ensure at least ID, Name, Dept, Salary exist
                    employees.add(cols);
                }
            }

            // Sort by salary in descending order
            employees.sort((emp1, emp2) -> { // Explicitly define the comparator for String[]
                try {
                    double salary1 = Double.parseDouble(emp1[3].trim());
                    double salary2 = Double.parseDouble(emp2[3].trim());
                    return Double.compare(salary2, salary1); // Descending order
                } catch (NumberFormatException e) {
                    // Handle invalid salary by considering them equal or placing at the end
                    return 0;
                }
            });

            System.out.println("\nTop " + TOP_N + " Highest Paid Employees (BufferedReader):");
            System.out.println("--------------------------------------------------");
            for (int i = 0; i < Math.min(TOP_N, employees.size()); i++) {
                String[] emp = employees.get(i);
                System.out.printf("ID: %-5s Name: %-10s Dept: %-10s Salary: %s%n",
                        emp[0].trim(), emp[1].trim(), emp[2].trim(), emp[3].trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        findTopEmployeesBufferedReader();
    }
}
