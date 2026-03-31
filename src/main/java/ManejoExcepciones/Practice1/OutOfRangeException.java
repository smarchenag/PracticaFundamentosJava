package ManejoExcepciones.Practice1;

public class OutOfRangeException extends ValidationExcepton{
    private final int value;
    private final int min;
    private final int max;

    public OutOfRangeException(String field, int value, int min, int max) {
        super(field, String.format("El campo %s con valor %d debe estar entre %d y %d", field, value, min, max));
        this.value = value;
        this.min = min;
        this.max = max;
    }
}
