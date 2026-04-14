package model;

import com.sun.jmx.mbeanserver.NamedObject;

public class Account {
    private int accountId;
    private String name;
    private String email;
    private double balance;

    public Account(int accountId ,String name , String email , double balance){
        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public int getAccountId(){
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount){
        balance+= amount;
    }
    public boolean withdraw(double amount){
        if(balance < amount) return false;

        balance-= amount;
        return true;
    }
}
