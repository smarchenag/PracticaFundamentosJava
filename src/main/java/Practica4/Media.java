package Practica4;

public abstract class Media {

    private final String titulo;
    private final String autor;
    private final int anio;

    public Media(String titulo, String autor, int anio) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnio() {
        return anio;
    }

    public abstract String getType();

    public String describe() {
        return String.format("Titulo: %s, autor: %s, año: %s",titulo,autor,anio);
    }
}
