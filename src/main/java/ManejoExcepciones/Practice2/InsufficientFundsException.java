package ManejoExcepciones.Practice2;

public class InsufficientFundsException extends BankException{

    public InsufficientFundsException(double amount, double balance) {
        super(String.format("No hay fondos suficientes. Intentaste retirar $ %.2f pero solo tienes $ %.2f", amount, balance));
    }
}
