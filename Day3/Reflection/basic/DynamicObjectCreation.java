import java.lang.reflect.Constructor;

class Student {
    private String name;
    private int rollNumber;

    public Student() {
        System.out.println("Default Student constructor called.");
        this.name = "Default Student";
        this.rollNumber = 0;
    }

    public Student(String name, int rollNumber) {
        System.out.println("Parameterized Student constructor called.");
        this.name = name;
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', rollNumber=" + rollNumber + '}';
    }
}

public class DynamicObjectCreation {

    public static void main(String[] args) throws Exception {
        String className = "Student";
        Class<?> studentClass = Class.forName(className);

        // Get the default constructor and create an instance
        Constructor<?> defaultConstructor = studentClass.getDeclaredConstructor();
        Object student1 = defaultConstructor.newInstance();
        System.out.println("Dynamically created Student (default): " + student1);

        // Get the parameterized constructor (String, int) and create an instance
        Constructor<?> parameterizedConstructor = studentClass.getDeclaredConstructor(String.class, int.class);
        Object student2 = parameterizedConstructor.newInstance("Alice", 101);
        System.out.println("Dynamically created Student (parameterized): " + student2);
    }
}