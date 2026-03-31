package ManejoExcepciones.Practice1;

public class EmptyFieldException extends ValidationExcepton{
    public EmptyFieldException(String field) {
        super(field, String.format("El campo %s no puede estar vacio",field));
    }
}
