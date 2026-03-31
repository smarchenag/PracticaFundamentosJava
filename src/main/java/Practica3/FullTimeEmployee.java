package Practica3;

public class FullTimeEmployee extends Employee implements Taxable{

    private double salary;

    public FullTimeEmployee(String id, String name, String department,double salary) {
        super(id, name, department);
        this.salary = salary;
    }

    @Override
    public double calculateSalary() {
        return salary;
    }

    @Override
    public double calculateTax() {
        return salary * 0.15;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
