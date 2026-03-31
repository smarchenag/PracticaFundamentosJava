package LambdasAndStreams.Practice1;

public class Employee {
    private String name;
    private String department;
    private double salary;
    private int age;
    private boolean active;

    public Employee(String name, String department, double salary, int age, boolean active) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.age = age;
        this.active = active;
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

    public int getAge() {
        return age;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                ", active=" + active +
                '}';
    }
}
