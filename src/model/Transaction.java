package model;

public class Transaction {
    public int transactionId;
    public int accountId;
    public String type;
    public double amount;

    public Transaction(int transactionId, int accountId, String type, double amount){
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
    }
}
