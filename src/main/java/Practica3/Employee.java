package Practica3;

public abstract class Employee {
    private String id;
    private String name;
    private String department;

    protected Employee(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
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

    public abstract double calculateSalary();

    public String describe() {
        return String.format("Employee[id=%s, name=%s, dept=%s, salary=%.2f]",
                id, name, department, calculateSalary());
    }
}
