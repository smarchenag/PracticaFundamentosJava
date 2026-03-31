package Practica1;

public class Jaula <T extends Ave> {
    private T animal;

    public void ingresarAnimal(T animal) {
        this.animal = animal;
    }
}
