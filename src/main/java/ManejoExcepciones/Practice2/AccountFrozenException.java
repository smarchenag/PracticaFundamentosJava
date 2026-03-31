package ManejoExcepciones.Practice2;

public class AccountFrozenException extends BankException{
    public AccountFrozenException() {
        super("La cuenta está congelada. Operación no permitida");
    }
}
