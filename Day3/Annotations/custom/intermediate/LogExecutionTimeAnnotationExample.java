package org.example.custom.intermediate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

// 1. Define the @LogExecutionTime annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface LogExecutionTime {
}

interface Task {
    void performTask1();
    String performTask2(String input);
    int performTask3(int count);
}

class TaskExecutor implements Task {
    @LogExecutionTime
    @Override
    public void performTask1() {
        try {
            Thread.sleep(200); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task 1 executed.");
    }

    @LogExecutionTime
    @Override
    public String performTask2(String input) {
        try {
            Thread.sleep(50); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Processed: " + input.toUpperCase();
    }

    @LogExecutionTime
    @Override
    public int performTask3(int count) {
        int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += i;
        }
        try {
            Thread.sleep(100); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return sum;
    }

    public void performRegularTask() {
        System.out.println("Regular task executed.");
    }
}

class TimingInvocationHandler implements InvocationHandler {
    private final Object target;

    public TimingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(LogExecutionTime.class)) {
            long startTime = System.nanoTime();
            Object result = method.invoke(target, args);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("Execution of method '" + method.getName() + "' took: " +
                    TimeUnit.NANOSECONDS.toMillis(duration) + " milliseconds.");
            return result;
        } else {
            return method.invoke(target, args);
        }
    }
}

public class LogExecutionTimeAnnotationExample {
    public static void main(String[] args) {
        TaskExecutor taskExecutor = new TaskExecutor();

        // Create a proxy to intercept method calls
        Task timedTaskExecutor = (Task) Proxy.newProxyInstance(
                TaskExecutor.class.getClassLoader(),
                new Class[]{Task.class},
                new TimingInvocationHandler(taskExecutor)
        );

        timedTaskExecutor.performTask1();
        String result2 = timedTaskExecutor.performTask2("hello");
        System.out.println("Result of Task 2: " + result2);
        int result3 = timedTaskExecutor.performTask3(100000);
        System.out.println("Result of Task 3: " + result3);

        taskExecutor.performRegularTask(); // This method's execution time will not be logged
    }
}
