package org.example.custom.beginner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

// 1. Define the @Todo annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Todo {
    String task();
    String assignedTo();
    String priority() default "MEDIUM";
}

class FeatureDevelopment {
    @Todo(task = "Implement user authentication", assignedTo = "Bob", priority = "HIGH")
    public void authenticateUser() {
        // Implementation pending
    }

    @Todo(task = "Integrate with payment gateway", assignedTo = "Alice")
    public void processPayment() {
        // Implementation pending
    }

    @Todo(task = "Add email notification service", assignedTo = "Charlie", priority = "LOW")
    public void sendEmail() {
        // Implementation pending
    }

    public void deployApplication() {
        System.out.println("Application deployed.");
    }
}

public class TodoAnnotationExample {
    public static void main(String[] args) throws Exception {
        Class<?> featureDevClass = FeatureDevelopment.class;
        Method[] methods = featureDevClass.getDeclaredMethods();

        // 3. Retrieve and print all pending tasks using Reflection
        System.out.println("Pending Tasks:");
        for (Method method : methods) {
            if (method.isAnnotationPresent(Todo.class)) {
                Todo todoTask = method.getAnnotation(Todo.class);
                System.out.println("  Method: " + method.getName());
                System.out.println("    Task: " + todoTask.task());
                System.out.println("    Assigned To: " + todoTask.assignedTo());
                System.out.println("    Priority: " + todoTask.priority());
            }
        }
    }
}