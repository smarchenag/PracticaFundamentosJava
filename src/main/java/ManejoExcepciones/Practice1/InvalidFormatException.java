package ManejoExcepciones.Practice1;

public class InvalidFormatException extends ValidationExcepton{
    private final String value;

    public InvalidFormatException(String field, String message, String value) {
        super(field, message);
        this.value = value;
    }

    public String getInvalidValue(){
        return value;
    }
}
