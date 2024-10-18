package org.example;

public class Account {
    private double balance;
    private String password;
    private String email;
    private Client client;

    public Account(double balance, String password, String email, Client client) {
        this.balance = balance;
        this.password = password;
        this.email = email;
        this.client = client;
    }

    public boolean cashOut(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer(double amount, Account recipient) {
        if (this.balance >= amount) {
            this.balance -= amount;
            recipient.deposit(amount);
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public String getEmail() {
        return email;
    }

    public Client getClient() {
        return client;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }
}
