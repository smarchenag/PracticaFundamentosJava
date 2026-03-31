package ManejoExcepciones.Practice2;

public class BankAccount {
    private String accountNumber;
    private String ownerName;
    private double balance;
    private boolean frozen;

    public BankAccount(String ownerName, String accountNumber, double saldoInicial) {
        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
        this.balance = saldoInicial;
        this.frozen = false;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (frozen) throw new AccountFrozenException();
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new InvalidAmountException(amount);
        if (frozen) throw new AccountFrozenException();
        if (amount > balance) throw new InsufficientFundsException(amount, balance);
        balance -= amount;
    }

    public void freeze() {
        frozen = true;
    }
    public void unfreeze() {
        frozen = false;
    }

    public double getBalance() {
        return balance;
    }

    public String getSummary(){
        return String.format("ownerName: %s, accountNumber: %s, balance: %.2f, isFrozen: %b", ownerName,accountNumber,balance,frozen);
    }
}
