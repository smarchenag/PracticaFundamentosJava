package Practica3;

public class Contractor extends Employee implements Taxable{

    private double dailyRate;
    private int daysWorked;

    public Contractor(String id, String name, String department,double dailyRate, int daysWorked) {
        super(id, name, department);
        this.dailyRate = dailyRate;
        this.daysWorked = daysWorked;
    }

    @Override
    public double calculateSalary() {
        return dailyRate * daysWorked;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }

    @Override
    public double calculateTax() {
        return this.calculateSalary() * 0.22;
    }
}
