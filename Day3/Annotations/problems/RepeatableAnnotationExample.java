package org.example.problems;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

// 1. Define the @BugReport annotation with a description field.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(BugReports.class) // 2. Use @Repeatable to allow multiple bug reports.
@interface BugReport {
    String description();
}

// Container annotation for repeatable @BugReport
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BugReports {
    BugReport[] value();
}

class CodeWithBugs {
    // 3. Apply @BugReport annotation multiple times on a method.
    @BugReport(description = "NullPointerException occurs when input is empty.")
    @BugReport(description = "Incorrect calculation for negative numbers.")
    public int calculateSomething(int input) {
        if (input < 0) {
            return 0; // Incorrect logic for negative numbers
        }
        // Potential NullPointerException if input processing isn't careful
        if (String.valueOf(input).isEmpty()) {
            throw new NullPointerException("Input string is empty.");
        }
        return input * 2;
    }

    public void anotherMethod() {
        System.out.println("Another method without bug reports.");
    }
}

public class RepeatableAnnotationExample {
    public static void main(String[] args) throws Exception {
        Class<?> codeWithBugsClass = CodeWithBugs.class;
        Method calculateMethod = codeWithBugsClass.getDeclaredMethod("calculateSomething", int.class);

        // 4. Retrieve and print all bug reports.
        if (calculateMethod.isAnnotationPresent(BugReports.class)) {
            BugReports bugReportsContainer = calculateMethod.getAnnotation(BugReports.class);
            BugReport[] allBugReports = bugReportsContainer.value();
            System.out.println("Bug reports for method: " + calculateMethod.getName());
            for (BugReport bugReport : allBugReports) {
                System.out.println("  Description: " + bugReport.description());
            }
        } else if (calculateMethod.isAnnotationPresent(BugReport.class)) {
            // Handle the case where only one @BugReport is present (though with @Repeatable, BugReports should be used)
            BugReport singleBugReport = calculateMethod.getAnnotation(BugReport.class);
            System.out.println("Bug report for method: " + calculateMethod.getName());
            System.out.println("  Description: " + singleBugReport.description());
        } else {
            System.out.println("No bug reports found for method: " + calculateMethod.getName());
        }

        Method anotherMethod = codeWithBugsClass.getDeclaredMethod("anotherMethod");
        if (!anotherMethod.isAnnotationPresent(BugReports.class) && !anotherMethod.isAnnotationPresent(BugReport.class)) {
            System.out.println("\nNo bug reports found for method: " + anotherMethod.getName());
        }
    }
}
