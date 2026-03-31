package Practica1;

public class Aguila extends Ave implements Volar {
    @Override
    public void volar() {
        System.out.println("El aguila está volando");
    }
}
