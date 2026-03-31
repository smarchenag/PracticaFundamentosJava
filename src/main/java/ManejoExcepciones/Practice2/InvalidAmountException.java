package ManejoExcepciones.Practice2;

public class InvalidAmountException extends BankException{
    public InvalidAmountException(double amount) {
        super(String.format("El monto $ %.2f no es válido. Debe ser mayor a cero", amount));
    }
}
