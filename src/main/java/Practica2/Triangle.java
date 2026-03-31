package Practica2;

public class Triangle extends Shape{

    private Double base;
    private Double height;
    private Double ladoA;
    private Double ladoB;
    private Double ladoC;

    public Triangle(Double base, Double height, Double ladoA, Double ladoB, Double ladoC){
        this.base = base;
        this.height = height;
        this.ladoA = ladoA;
        this.ladoB = ladoB;
        this.ladoC = ladoC;
    }

    @Override
    public Double area() {
        return (base*height)/2;
    }

    @Override
    public Double perimeter() {
        return ladoA+ladoB+ladoC;
    }

    @Override
    public String describe() {
        return String.format("Triangle con base=%.2f, altura=%.2f, perímetro=%.2f", base, height, perimeter());
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "base=" + base +
                ", height=" + height +
                ", ladoA=" + ladoA +
                ", ladoB=" + ladoB +
                ", ladoC=" + ladoC +
                '}';
    }
}
