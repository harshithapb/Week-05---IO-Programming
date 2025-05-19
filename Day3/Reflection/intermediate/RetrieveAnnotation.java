package level.intermediate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//import java.lang.reflect.Annotation;

// Define the custom annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // Apply to classes
@interface Author {
    String name();
}

// Apply the Author annotation to a class
@Author(name = "John Doe")
class Book {
    private String title;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

public class RetrieveAnnotation {

    public static void main(String[] args) {
        Class<?> bookClass = Book.class;

        // Check if the Author annotation is present on the class
        if (bookClass.isAnnotationPresent(Author.class)) {
            // Get the Author annotation
            Author authorAnnotation = bookClass.getAnnotation(Author.class);

            // Retrieve the value of the 'name' element
            String authorName = authorAnnotation.name();

            // Display the annotation value
            System.out.println("Author of the Book: " + authorName);
        } else {
            System.out.println("The Author annotation is not present on the Book class.");
        }
    }
}