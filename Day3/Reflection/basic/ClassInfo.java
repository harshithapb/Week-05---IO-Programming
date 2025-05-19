package level.basic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

class Employee{
    private String name;
    public int age;
}

public class ClassInfo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the fully qualified class name (e.g., java.util.ArrayList): ");
        String className = scanner.nextLine();
        scanner.close();

        try {
            // Load the class using its name
            Class<?> clazz = Class.forName(className);

            System.out.println("\nInformation for Class: " + clazz.getName());

            // Display Constructors
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            System.out.println("\nConstructors:");
            if (constructors.length > 0) {
                for (Constructor<?> constructor : constructors) {
                    System.out.println("  " + constructor.getName() + "(" +
                            String.join(", ", Arrays.stream(constructor.getParameterTypes())
                                    .map(Class::getSimpleName)
                                    .toArray(String[]::new)) + ")");
                }
            } else {
                System.out.println("  No declared constructors found.");
            }

            // Display Fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("\nFields:");
            if (fields.length > 0) {
                for (Field field : fields) {
                    System.out.println("  " + field.getType().getSimpleName() + " " + field.getName());
                }
            } else {
                System.out.println("  No declared fields found.");
            }

            // Display Methods
            Method[] methods = clazz.getDeclaredMethods();
            System.out.println("\nMethods:");
            if (methods.length > 0) {
                for (Method method : methods) {
                    System.out.println("  " + method.getReturnType().getSimpleName() + " " + method.getName() + "(" +
                            String.join(", ", Arrays.stream(method.getParameterTypes())
                                    .map(Class::getSimpleName)
                                    .toArray(String[]::new)) + ")");
                }
            } else {
                System.out.println("  No declared methods found.");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error: Class not found - " + className);
        } catch (SecurityException e) {
            System.err.println("Error: Security exception occurred while accessing class information - " + e.getMessage());
        }
    }

}
