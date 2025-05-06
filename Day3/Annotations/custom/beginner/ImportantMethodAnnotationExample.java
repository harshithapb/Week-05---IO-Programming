package org.example.custom.beginner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

// 1. Define the custom annotation @ImportantMethod
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ImportantMethod {
    String level() default "HIGH";
}

class TaskProcessor {
    @ImportantMethod
    public void processCriticalData() {
        System.out.println("Processing critical data...");
    }

    @ImportantMethod(level = "MEDIUM")
    public void performSecondaryTask() {
        System.out.println("Performing a secondary task...");
    }

    public void performRoutineOperation() {
        System.out.println("Performing a routine operation...");
    }
}

public class ImportantMethodAnnotationExample {
    public static void main(String[] args) throws Exception {
        Class<?> taskProcessorClass = TaskProcessor.class;
        Method[] methods = taskProcessorClass.getDeclaredMethods();

        // 3. Retrieve and print annotated methods using Reflection API
        System.out.println("Important Methods:");
        for (Method method : methods) {
            if (method.isAnnotationPresent(ImportantMethod.class)) {
                ImportantMethod importantAnnotation = method.getAnnotation(ImportantMethod.class);
                System.out.println("  Method Name: " + method.getName());
                System.out.println("  Importance Level: " + importantAnnotation.level());
            }
        }
    }
}
