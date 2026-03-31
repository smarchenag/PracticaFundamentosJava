package Practica1;

public class Cuidador {

    public void alimentarAve(Ave ave) {
        System.out.println("Alimentando ave...");
        ave.comer();
    }

    public void revisarJaula(Jaula<? extends Ave> jaula) {
        System.out.println("Revisando jaula...");
    }
}
