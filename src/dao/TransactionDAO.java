package dao;

import util.DBConnection;
import java.sql.*;

public class TransactionDAO {

    public void deposit(int accountId,double amount) throws Exception{
        Connection con = DBConnection.getConnection();

        // Update balance
        String sql="UPDATE accounts SET balance=balance+? WHERE account_id=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setDouble(1,amount);
        ps.setInt(2,accountId);
        ps.executeUpdate();

        // Log transaction
        String insertTxn = "INSERT INTO transactions(account_id,type,amount,date) VALUES(?,?,?,NOW())";
        PreparedStatement psTxn = con.prepareStatement(insertTxn);
        psTxn.setInt(1, accountId);
        psTxn.setString(2, "DEPOSIT");
        psTxn.setDouble(3, amount);
        psTxn.executeUpdate();
        psTxn.close();

        System.out.println("Money deposited");
        ps.close();
        con.close();
    }

    public void withdraw(int accountId,double amount) throws Exception{
        Connection con = DBConnection.getConnection();

        String checkBalance = "SELECT balance FROM accounts WHERE account_id=?";
        PreparedStatement ps1 = con.prepareStatement(checkBalance);
        ps1.setInt(1,accountId);
        ResultSet rs = ps1.executeQuery();

        if(rs.next()){
            double balance = rs.getDouble("balance");
            if(balance < amount){
                System.out.println("Insufficient Balance");
                return;
            }

            String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_id=?";
            PreparedStatement ps2 = con.prepareStatement(withdrawQuery);
            ps2.setDouble(1,amount);
            ps2.setInt(2,accountId);
            ps2.executeUpdate();

            // Log transaction
            String insertTxn = "INSERT INTO transactions(account_id,type,amount,date) VALUES(?,?,?,NOW())";
            PreparedStatement psTxn = con.prepareStatement(insertTxn);
            psTxn.setInt(1, accountId);
            psTxn.setString(2, "WITHDRAW");
            psTxn.setDouble(3, amount);
            psTxn.executeUpdate();
            psTxn.close();

            System.out.println("Withdrawal Successful");
            ps2.close();
        }

        ps1.close();
        con.close();
    }

    public void transfer(int senderId,int receiverId,double amount) throws Exception{
        Connection con = DBConnection.getConnection();
        con.setAutoCommit(false);

        try{
            // Check sender balance
            String check = "SELECT balance FROM accounts WHERE account_id=?";
            PreparedStatement ps1 = con.prepareStatement(check);
            ps1.setInt(1,senderId);
            ResultSet rs = ps1.executeQuery();

            if(rs.next()){
                double balance = rs.getDouble("balance");
                if(balance < amount){
                    System.out.println("Insufficient Balance");
                    return;
                }
            }

            // Debit sender
            String debit = "UPDATE accounts SET balance = balance - ? WHERE account_id=?";
            PreparedStatement ps2 = con.prepareStatement(debit);
            ps2.setDouble(1,amount);
            ps2.setInt(2,senderId);
            ps2.executeUpdate();

            // Log sender transaction
            String txnSender = "INSERT INTO transactions(account_id,type,amount,date) VALUES(?,?,?,NOW())";
            PreparedStatement psTxnSender = con.prepareStatement(txnSender);
            psTxnSender.setInt(1, senderId);
            psTxnSender.setString(2, "TRANSFER_DEBIT");
            psTxnSender.setDouble(3, amount);
            psTxnSender.executeUpdate();
            psTxnSender.close();

            // Credit receiver
            String credit = "UPDATE accounts SET balance = balance + ? WHERE account_id=?";
            PreparedStatement ps3 = con.prepareStatement(credit);
            ps3.setDouble(1,amount);
            ps3.setInt(2,receiverId);
            ps3.executeUpdate();

            // Log receiver transaction
            String txnReceiver = "INSERT INTO transactions(account_id,type,amount,date) VALUES(?,?,?,NOW())";
            PreparedStatement psTxnReceiver = con.prepareStatement(txnReceiver);
            psTxnReceiver.setInt(1, receiverId);
            psTxnReceiver.setString(2, "TRANSFER_CREDIT");
            psTxnReceiver.setDouble(3, amount);
            psTxnReceiver.executeUpdate();
            psTxnReceiver.close();

            con.commit();
            System.out.println("Transfer Successful");

            ps1.close();
            ps2.close();
            ps3.close();
            con.close();

        } catch(Exception e){
            con.rollback();
            System.out.println("Transfer Failed");
            con.close();
        }
    }

    public void checkBalance(int accountId) throws Exception{
        Connection con = DBConnection.getConnection();
        String sql = "SELECT balance FROM accounts WHERE account_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,accountId);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            double bal = rs.getDouble("balance");
            System.out.println("Current Balance: " + bal);
        } else {
            System.out.println("Account not found");
        }

        rs.close();
        ps.close();
        con.close();
    }
}