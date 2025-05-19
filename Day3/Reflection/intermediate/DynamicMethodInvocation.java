package level.intermediate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

class MathOperations {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }
}

public class DynamicMethodInvocation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MathOperations mathOps = new MathOperations();
        Class<?> mathOpsClass = mathOps.getClass();

        try {
            System.out.println("Available operations: " +
                    Arrays.stream(mathOpsClass.getDeclaredMethods())
                            .map(Method::getName)
                            .filter(name -> !name.equals("main") && !name.startsWith("get") && !name.equals("greet")) // Basic filtering
                            .toList());

            System.out.print("Enter the method name to invoke: ");
            String methodName = scanner.nextLine();

            Method methodToInvoke = null;
            Method[] methods = mathOpsClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    methodToInvoke = method;
                    break;
                }
            }

            if (methodToInvoke != null) {
                Class<?>[] parameterTypes = methodToInvoke.getParameterTypes();
                Object[] arguments = new Object[parameterTypes.length];

                System.out.println("Enter arguments for method '" + methodName + "' (" + parameterTypes.length + " arguments expected):");
                for (int i = 0; i < parameterTypes.length; i++) {
                    System.out.print("Argument " + (i + 1) + " (" + parameterTypes[i].getSimpleName() + "): ");
                    if (parameterTypes[i] == int.class) {
                        arguments[i] = scanner.nextInt();
                    } else if (parameterTypes[i] == String.class) {
                        arguments[i] = scanner.next();
                    } else {
                        System.out.println("Unsupported parameter type: " + parameterTypes[i].getSimpleName());
                        return;
                    }
                }
                scanner.nextLine(); // Consume the newline character

                // Invoke the method
                Object result = methodToInvoke.invoke(mathOps, arguments);

                if (result != null) {
                    System.out.println("Result of " + methodName + ": " + result);
                } else {
                    System.out.println(methodName + " executed successfully.");
                }

            } else {
                System.out.println("Method '" + methodName + "' not found.");
            }

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
