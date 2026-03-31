package Practica2;

public class Rectangle extends Shape implements Drawable {

    private Double base;
    private Double altura;

    public Rectangle(Double base, Double altura){
        this.base = base;
        this.altura = altura;
    }

    @Override
    public Double area() {
        return base*altura;
    }

    @Override
    public Double perimeter() {
        return 2 * (base+altura);
    }

    @Override
    public String describe() {
        return String.format("Rectangle con base=%.2f, altura=%.2f, area=%.2f", base, altura, area());
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "base=" + base +
                ", altura=" + altura +
                ", area=" + area() +
                '}';
    }

    @Override
    public void draw() {
        System.out.println("drawing Rectangle");
    }
}
