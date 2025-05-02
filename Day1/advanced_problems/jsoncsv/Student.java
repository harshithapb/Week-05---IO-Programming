package org.example.jsoncsv;

 // Replace with your actual package

public class Student {
    private String id;
    private String name;
    private String department;
    private double salary;

    // Default constructor (add this if it's missing)
    public Student() {
    }

    // Your existing constructor with arguments
    public Student(String id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}
