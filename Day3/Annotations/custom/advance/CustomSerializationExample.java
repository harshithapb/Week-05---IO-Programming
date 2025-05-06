package org.example.custom.advance;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

// 1. Define the @JsonField annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface JsonField {
    String name() default ""; // Custom name for the JSON key
}

class User {
    @JsonField(name = "user_name")
    private String username;

    @JsonField
    private int age;

    private String password; // This field should not be serialized

    @JsonField(name = "email_address")
    private String email;

    public User(String username, int age, String password, String email) {
        this.username = username;
        this.age = age;
        this.password = password;
        this.email = email;
    }

    // Getters (for simplicity, assuming they exist)
    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
}

public class CustomSerializationExample {

    public static String toJson(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return "null";
        }

        Class<?> clazz = obj.getClass();
        StringBuilder jsonBuilder = new StringBuilder("{");
        Field[] fields = clazz.getDeclaredFields();
        boolean firstField = true;

        for (Field field : fields) {
            if (field.isAnnotationPresent(JsonField.class)) {
                field.setAccessible(true);
                JsonField jsonFieldAnnotation = field.getAnnotation(JsonField.class);
                String jsonKey = jsonFieldAnnotation.name().isEmpty() ? field.getName() : jsonFieldAnnotation.name();
                Object fieldValue = field.get(obj);

                if (!firstField) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append(escapeString(jsonKey)).append(":");
                jsonBuilder.append(toJsonValue(fieldValue));
                firstField = false;
            }
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private static String toJsonValue(Object value) throws IllegalAccessException {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return escapeString((String) value);
        } else if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        } else if (value.getClass().isArray()) {
            return toJsonArray(value);
        } else if (value instanceof Iterable) {
            return toJsonIterable((Iterable<?>) value);
        } else {
            return toJson(value); // Recursive call for nested objects
        }
    }

    private static String toJsonArray(Object array) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder("[");
        int length = java.lang.reflect.Array.getLength(array);
        for (int i = 0; i < length; i++) {
            sb.append(toJsonValue(java.lang.reflect.Array.get(array, i)));
            if (i < length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static String toJsonIterable(Iterable<?> iterable) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Object item : iterable) {
            if (!first) {
                sb.append(",");
            }
            sb.append(toJsonValue(item));
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }

    private static String escapeString(String str) {
        return "\"" + str.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    public static void main(String[] args) throws IllegalAccessException {
        User user = new User("john.doe", 30, "secret", "john.doe@example.com");
        String jsonOutput = toJson(user);
        System.out.println(jsonOutput);
    }
}