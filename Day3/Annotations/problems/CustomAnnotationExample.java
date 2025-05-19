package org.example.problems;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

// 1. Define the custom annotation @TaskInfo
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface TaskInfo {
    String priority() default "MEDIUM";
    String assignedTo() default "Unassigned";
}

class TaskManager {
    // 2. Apply the @TaskInfo annotation to a method
    @TaskInfo(priority = "HIGH", assignedTo = "Alice")
    public void processData() {
        System.out.println("Processing data...");
    }

    @TaskInfo(assignedTo = "Bob") // Using default priority
    public void generateReport() {
        System.out.println("Generating report...");
    }

    public void cleanup() {
        System.out.println("Cleaning up...");
    }
}

public class CustomAnnotationExample {
    public static void main(String[] args) throws Exception {
        Class<?> taskManagerClass = TaskManager.class;
        Method[] methods = taskManagerClass.getDeclaredMethods();

        // 3. Retrieve the annotation details using Reflection API
        for (Method method : methods) {
            if (method.isAnnotationPresent(TaskInfo.class)) {
                TaskInfo taskInfo = method.getAnnotation(TaskInfo.class);
                System.out.println("Method: " + method.getName());
                System.out.println("  Priority: " + taskInfo.priority());
                System.out.println("  Assigned To: " + taskInfo.assignedTo());
                System.out.println("--------------------");
            }
        }
    }
}