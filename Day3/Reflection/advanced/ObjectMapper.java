package level.advanced;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ObjectMapper {

    public static <T> T toObject(Class<T> clazz, Map<String, Object> properties)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        T obj = clazz.getDeclaredConstructor().newInstance();

        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            Field field = null; // Declare 'field' outside the try block

            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);

                Object convertedValue = convertType(value, field.getType());
                field.set(obj, convertedValue);

            } catch (NoSuchFieldException e) {
                System.err.println("Warning: Field '" + fieldName + "' not found in class " + clazz.getName());
            } catch (IllegalArgumentException e) {
                if (field != null) { // Only access 'field' if it was successfully retrieved
                    System.err.println("Warning: Incorrect argument type for field '" + fieldName + "' in class " + clazz.getName() + ". Expected " + field.getType().getName() + ", got " + value.getClass().getName());
                } else {
                    System.err.println("Warning: Incorrect argument type for field '" + fieldName + "' in class " + clazz.getName() + ". Could not determine expected type.");
                }
            }
        }

        return obj;
    }
    private static Object convertType(Object value, Class<?> targetType) {
        if (value == null || targetType.isInstance(value)) {
            return value;
        }

        String valueStr = String.valueOf(value);

        if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(valueStr);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(valueStr);
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(valueStr);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(valueStr);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(valueStr);
        }
        // Add more type conversions as needed

        return value; // Return original value if no conversion is defined
    }

    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Map<String, Object> personProperties = Map.of(
                "name", "Alice",
                "age", "30",
                "city", "Chennai",
                "isEmployed", "true",
                "salary", "50000.50"
        );

        Person personObject = ObjectMapper.toObject(Person.class, personProperties);
        System.out.println("Person Object: " + personObject);

        Map<String, Object> configProperties = Map.of(
                "apiKey", "your_api_key_here",
                "maxConnections", "10",
                "timeoutMillis", "5000"
        );

        Configuration configObject = ObjectMapper.toObject(Configuration.class, configProperties);
        System.out.println("Configuration Object: " + configObject);
    }
}

class Person {
    private String name;
    private int age;
    private String city;
    private boolean isEmployed;
    private double salary;

    // Default constructor is required for newInstance()
     Person() {
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public boolean isEmployed() {
        return isEmployed;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", isEmployed=" + isEmployed +
                ", salary=" + salary +
                '}';
    }
}

class Configuration {
    private String apiKey;
    private int maxConnections;
    private long timeoutMillis;

    public Configuration() {
    }

    public String getApiKey() {
        return apiKey;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "apiKey='" + apiKey + '\'' +
                ", maxConnections=" + maxConnections +
                ", timeoutMillis=" + timeoutMillis +
                '}';
    }
}
