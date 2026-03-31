package Practica2;

public class Circle extends Shape implements Drawable {

    private Double radio;

    public Circle(Double radio){
        this.radio = radio;
    }

    @Override
    public Double area() {
        return Math.PI * Math.pow(radio, 2);
    }

    @Override
    public Double perimeter() {
        return 2 * Math.PI * radio;
    }

    @Override
    public String describe() {
        return String.format("Circle con radio=%.2f, area=%.2f, perímetro=%.2f", radio, area(), perimeter());
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radio=" + radio +
                ", area=" + area() +
                '}';
    }

    @Override
    public void draw() {
        System.out.println("drawing Circle");
    }
}
