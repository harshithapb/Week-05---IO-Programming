package org.example.custom.intermediate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

// 1. Define the @MaxLength annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class User {
    @MaxLength(15)
    private String username;

    public User(String username) {
        this.username = validateMaxLength(username);
    }

    public String getUsername() {
        return username;
    }

    private String validateMaxLength(String value) {
        try {
            Field usernameField = this.getClass().getDeclaredField("username");
            if (usernameField.isAnnotationPresent(MaxLength.class)) {
                MaxLength maxLengthAnnotation = usernameField.getAnnotation(MaxLength.class);
                int maxLength = maxLengthAnnotation.value();
                if (value != null && value.length() > maxLength) {
                    throw new IllegalArgumentException("Username exceeds maximum length of " + maxLength + " characters.");
                }
            }
        } catch (NoSuchFieldException e) {
            // Should not happen for the 'username' field in this class
            e.printStackTrace();
        }
        return value;
    }
}

public class MaxLengthAnnotationExample {
    public static void main(String[] args) {
        try {
            User user1 = new User("john.doe");
            System.out.println("User 1 created: " + user1.getUsername());

            User user2 = new User("thisusernameistoolong");
            System.out.println("User 2 created: " + user2.getUsername()); // This line will not be reached

        } catch (IllegalArgumentException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
    }
}