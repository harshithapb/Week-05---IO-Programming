package org.example.problems;

import java.util.ArrayList;
import java.util.List;

public class SuppressUncheckedWarningExample {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        // Creating an ArrayList without specifying a generic type
        List numbers = new ArrayList();

        // Adding elements of different types (which would cause an unchecked warning)
        numbers.add(10);
        numbers.add("twenty");
        numbers.add(3.14);

        // Iterating through the list (potential runtime errors due to mixed types)
        for (Object number : numbers) {
            System.out.println("Element: " + number);
            // Be cautious when performing type-specific operations
            if (number instanceof Integer) {
                int value = (int) number;
                System.out.println("Integer value: " + value);
            }
        }

        // Demonstrating adding to a generically typed list from the raw list
        List<Integer> integerList = new ArrayList<>();
        // The following line would cause an unchecked warning without @SuppressWarnings
        integerList.addAll(numbers);
        System.out.println("Integer List after adding all: " + integerList);
    }
}