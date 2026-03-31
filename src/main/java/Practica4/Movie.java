package Practica4;

public class Movie extends Media implements Playable,Ratable{

    private int duracion;
    private final RatingManager ratingManager = new RatingManager();

    public Movie(String titulo, String autor, int anio, int duracion) {
        super(titulo, autor, anio);
        this.duracion = duracion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void play() {
        System.out.println("Reproduciendo pelicula");
    }

    @Override
    public void pause() {
        System.out.println("Pausando pelicula");
    }

    @Override
    public void stop() {
        System.out.println("Pelicula detenida o finalizada");
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
        return this.describe() + ", Movie{" +
                "duracion=" + duracion +
                ", rating=" + ratingManager.getRatings()+
                '}';
    }
}
