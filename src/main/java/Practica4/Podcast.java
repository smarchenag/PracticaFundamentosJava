package Practica4;

public class Podcast extends Media implements Playable{
    private int episodios;

    public Podcast(String titulo, String autor, int anio, int episodios) {
        super(titulo, autor, anio);
        this.episodios = episodios;
    }

    public int getEpisodios() {
        return episodios;
    }

    public void setEpisodios(int episodios) {
        this.episodios = episodios;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void play() {
        System.out.println("Reproduciendo el podcast");
    }

    @Override
    public void pause() {
        System.out.println("Pausando el podcast");
    }

    @Override
    public void stop() {
        System.out.println("Podcast detenido o finalizado");
    }

    @Override
    public String toString() {
        return this.describe() + ", Podcast{" +
                "episodios=" + episodios +
                '}';
    }
}
