package LambdasAndStreams.Practice5;

public class Student {
    private String name;
    private String career;
    private double gpa;
    private int age;

    public Student(String name, String career, double gpa, int age) {
        this.name = name;
        this.career = career;
        this.gpa = gpa;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getCareer() {
        return career;
    }

    public double getGpa() {
        return gpa;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", career='" + career + '\'' +
                ", gpa=" + gpa +
                ", age=" + age +
                '}';
    }
}
