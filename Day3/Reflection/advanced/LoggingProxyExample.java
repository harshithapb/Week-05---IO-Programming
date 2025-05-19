package level.advanced;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

interface Greeting {
    void sayHello(String name);
    String sayGoodbye(String name);
}

class RealGreeting implements Greeting {
    @Override
    public void sayHello(String name) {
        System.out.println("RealGreeting: Hello, " + name + "!");
    }

    @Override
    public String sayGoodbye(String name) {
        return "RealGreeting: Goodbye, " + name + "!";
    }
}

class LoggingInvocationHandler implements InvocationHandler {
    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(dtf.format(LocalDateTime.now()) + " - Logging Proxy: Calling method '" + method.getName() +
                "' with arguments: " + (args == null ? "[]" : Arrays.toString(args)));
        Object result = method.invoke(target, args);
        System.out.println(dtf.format(LocalDateTime.now()) + " - Logging Proxy: Method '" + method.getName() + "' completed.");
        return result;
    }
}

public class LoggingProxyExample {
    public static void main(String[] args) {
        RealGreeting realGreeting = new RealGreeting();

        // Create a logging proxy for the Greeting interface
        Greeting loggingProxy = (Greeting) Proxy.newProxyInstance(
                Greeting.class.getClassLoader(),
                new Class[]{Greeting.class},
                new LoggingInvocationHandler(realGreeting)
        );

        // Call methods on the proxy object
        loggingProxy.sayHello("World");
        String goodbyeMessage = loggingProxy.sayGoodbye("User");
        System.out.println("Main: " + goodbyeMessage);
    }
}