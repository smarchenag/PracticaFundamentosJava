package Practica4;

public class Music extends Media implements Playable {
    private String artista;
    private int duracion;

    public Music(String titulo, String autor, int anio, String artista,int duracion) {
        super(titulo, autor, anio);
        this.artista = artista;
        this.duracion = duracion;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
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
        System.out.println("Reproduciendo musica");
    }

    @Override
    public void pause() {
        System.out.println("Pausando musica");
    }

    @Override
    public void stop() {
        System.out.println("Musica   detenida o finalizada");
    }

    @Override
    public String toString() {
        return this.describe() + ", Music{" +
                "artista='" + artista + '\'' +
                "duracion='" + duracion + '\'' +
                '}';
    }
}
