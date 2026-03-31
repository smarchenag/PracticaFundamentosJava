package Practica4;

public class Book extends Media implements Ratable{
    private final RatingManager ratingManager = new RatingManager();
    private int paginas;

    public Book(String titulo, String autor, int anio, int paginas) {
        super(titulo, autor, anio);
        this.paginas = paginas;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void rate(int stars) {
        ratingManager.addRating(stars);
    }

    @Override
    public double getAverageRating() {
        return ratingManager.getAverage();
    }

    @Override
    public String toString() {
        return this.describe() + ", " +"Book{" +
                "paginas=" + paginas +
                ", ratings=" + ratingManager.getRatings() +
                '}';
    }
}
