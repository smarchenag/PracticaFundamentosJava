package ManejoExcepciones.Practice3;

import ManejoExcepciones.Practice2.BankAccount;
import ManejoExcepciones.Practice2.BankException;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    Map<String, BankAccount> accounts = new HashMap<>();

    public BankAccount getAccount(String id) {
        BankAccount account = accounts.get(id);
        if (account == null) throw new RuntimeException("Cuenta no encontrada: " + id);
        return account;
    }

    public void addAccount(String id, BankAccount account){
        if(!accounts.containsKey(id)){
            accounts.put(id, account);
        }
    }

    public void transfer (String fromId, String toId, double amount) {
        BankAccount fromAccount = getAccount(fromId);
        BankAccount toAccount = getAccount(toId);
        fromAccount.withdraw(amount);
        try {
            toAccount.deposit(amount);
        } catch (BankException e){
            fromAccount.deposit(amount);
            throw e;
        }

    }
}
