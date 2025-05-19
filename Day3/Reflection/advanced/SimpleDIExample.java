package level.advanced;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;

// Custom Inject annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Inject {
}

// Example dependency
interface Service {
    void serve();
}

class SimpleService implements Service {
    @Override
    public void serve() {
        System.out.println("SimpleService is serving.");
    }
}

// Example class that depends on Service
class Client {
    @Inject
    private Service service;

    public void process() {
        if (service != null) {
            System.out.print("Client is processing with ");
            service.serve();
        } else {
            System.out.println("Client has no service.");
        }
    }
}

// Simple DI Container
class Container {
    private final Map<Class<?>, Object> instances = new HashMap<>();

    public void register(Class<?> type, Object instance) {
        instances.put(type, instance);
    }

    public <T> T get(Class<T> type) {
        return type.cast(instances.get(type));
    }

    public void processDependencies(String packageName) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> injectableClasses = reflections.getTypesAnnotatedWith(javax.inject.Singleton.class); // Using javax.inject.Singleton as an example for injectable classes

        // Instantiate singleton classes
        for (Class<?> clazz : injectableClasses) {
            if (!instances.containsKey(clazz)) {
                Constructor<?>[] constructors = clazz.getDeclaredConstructors();
                if (constructors.length == 1 && constructors[0].getParameterCount() == 0) {
                    Object instance = constructors[0].newInstance();
                    instances.put(clazz, instance);
                    System.out.println("Container: Created singleton instance of " + clazz.getName());
                } else {
                    System.err.println("Container: Cannot create singleton instance of " + clazz.getName() + ". Requires a no-arg constructor.");
                }
            }
        }

        // Process @Inject annotations
        Set<Class<?>> classesWithInject = reflections.getTypesAnnotatedWith(new java.lang.annotation.Annotation() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return Inject.class;
            }
        });

        for (Class<?> clazz : classesWithInject) {
            Object instance = getOrCreate(clazz);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Class<?> dependencyType = field.getType();
                    Object dependencyInstance = getOrCreate(dependencyType);
                    field.setAccessible(true);
                    field.set(instance, dependencyInstance);
                    System.out.println("Container: Injected dependency of type " + dependencyType.getName() + " into " + clazz.getName() + " field " + field.getName());
                }
            }
        }
    }

    private <T> T getOrCreate(Class<T> type) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (instances.containsKey(type)) {
            return type.cast(instances.get(type));
        }
        // Simple instantiation without constructor injection for this example
        Constructor<T> constructor = type.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance = constructor.newInstance();
        instances.put(type, instance);
        System.out.println("Container: Created instance of " + type.getName());
        return instance;
    }
}

public class SimpleDIExample {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Container container = new Container();

        // Register concrete implementations (could be done via scanning as well)
        container.register(Service.class, new SimpleService());

        // Process dependencies in the specified package
        container.processDependencies("com.example"); // Replace with your package name

        // Retrieve and use the Client object
        Client client = container.get(Client.class);
        if (client != null) {
            client.process();
        }
    }
}
