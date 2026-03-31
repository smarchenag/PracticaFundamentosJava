package ManejoExcepciones.Practice1;

public class ValidationExcepton extends RuntimeException{
    private final String message;
    public ValidationExcepton(String field,String message) {
        super(field+": "+message);
        this.message = message;
    }
}
