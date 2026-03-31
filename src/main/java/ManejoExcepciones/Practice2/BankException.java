package ManejoExcepciones.Practice2;

public class BankException extends RuntimeException{
    public BankException(String message){
        super(message);
    }
}
