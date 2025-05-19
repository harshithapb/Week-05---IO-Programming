package level.basic;

import java.lang.reflect.Field;

class Person {
    private int age;

    public Person(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{age=" + age + '}';
    }
}

public class AccessPrivateField {

    public static void main(String[] args) throws Exception {
        Person person = new Person(30);
        System.out.println("Original Person: " + person);

        // Get the Class object for Person
        Class<?> personClass = person.getClass();

        // Get the 'age' field (declared in Person)
        Field ageField = personClass.getDeclaredField("age");

        // Make the private field accessible (bypassing access modifiers)
        ageField.setAccessible(true);

        // Retrieve the value of the private 'age' field
        int currentAge = (int) ageField.get(person);
        System.out.println("Retrieved private age: " + currentAge);

        // Modify the value of the private 'age' field
        ageField.set(person, 35);
        System.out.println("Modified Person: " + person);

        // Retrieve the modified value
        int newAge = (int) ageField.get(person);
        System.out.println("Retrieved modified age: " + newAge);
    }
}