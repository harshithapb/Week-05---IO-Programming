package org.example.custom.advance;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// 1. Define the @CacheResult annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface CacheResult {
}

interface ExpensiveOperation {
    @CacheResult
    long computeFactorial(int n);

    String fetchData(String id);
}

class ExpensiveServiceImpl implements ExpensiveOperation {
    @Override
    public long computeFactorial(int n) {
        System.out.println("ExpensiveServiceImpl: Computing factorial of " + n);
        try {
            TimeUnit.MILLISECONDS.sleep(500); // Simulate expensive computation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (n < 0) {
            throw new IllegalArgumentException("Factorial of negative number is not defined");
        }
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    @Override
    public String fetchData(String id) {
        System.out.println("ExpensiveServiceImpl: Fetching data for ID: " + id);
        try {
            TimeUnit.MILLISECONDS.sleep(300); // Simulate expensive data fetching
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Data for ID " + id;
    }
}

class CachingInvocationHandler implements InvocationHandler {
    private final Object target;
    private final Map<MethodCacheKey, Object> cache = new HashMap<>();

    public CachingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(CacheResult.class)) {
            MethodCacheKey key = new MethodCacheKey(method, args);
            if (cache.containsKey(key)) {
                System.out.println("Cache hit for method '" + method.getName() + "' with arguments: " + Arrays.toString(args));
                return cache.get(key);
            } else {
                System.out.println("Cache miss for method '" + method.getName() + "' with arguments: " + Arrays.toString(args));
                Object result = method.invoke(target, args);
                cache.put(key, result);
                return result;
            }
        } else {
            return method.invoke(target, args);
        }
    }
}

// Helper class to represent the key for the cache
class MethodCacheKey {
    private final Method method;
    private final Object[] args;

    public MethodCacheKey(Method method, Object[] args) {
        this.method = method;
        this.args = args == null ? new Object[0] : args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodCacheKey that = (MethodCacheKey) o;
        return method.equals(that.method) && Arrays.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}

public class CustomCachingExample {
    public static void main(String[] args) {
        ExpensiveServiceImpl service = new ExpensiveServiceImpl();

        // Create a caching proxy for the ExpensiveOperation interface
        ExpensiveOperation cachedService = (ExpensiveOperation) Proxy.newProxyInstance(
                ExpensiveServiceImpl.class.getClassLoader(),
                new Class[]{ExpensiveOperation.class},
                new CachingInvocationHandler(service)
        );

        // First call - computation will happen and result will be cached
        System.out.println("First call:");
        long factorial5 = cachedService.computeFactorial(5);
        System.out.println("Result: " + factorial5);

        // Second call with the same input - result will be retrieved from cache
        System.out.println("\nSecond call:");
        long factorial5Again = cachedService.computeFactorial(5);
        System.out.println("Result: " + factorial5Again);

        // Call with different input - computation will happen and new result cached
        System.out.println("\nThird call:");
        long factorial6 = cachedService.computeFactorial(6);
        System.out.println("Result: " + factorial6);

        // Call fetchData - also cached
        System.out.println("\nFirst fetch:");
        String data1 = cachedService.fetchData("user123");
        System.out.println("Result: " + data1);

        System.out.println("\nSecond fetch:");
        String data2 = cachedService.fetchData("user123");
        System.out.println("Result: " + data2);
    }
}