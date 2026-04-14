package ui;

import java.sql.*;
import java.util.Scanner;
import dao.AccountDAO;
import dao.TransactionDAO;
import util.DBConnection;

public class BankController {

    Scanner sc = new Scanner(System.in);
    AccountDAO accountDAO = new AccountDAO();
    TransactionDAO transactionDAO = new TransactionDAO();

    public void start() throws Exception {

        while (true) {

            System.out.println("\n--- Banking Transaction System ---");
            System.out.println("1 Create Account");
            System.out.println("2 Deposit");
            System.out.println("3 Withdraw");
            System.out.println("4 Transfer");
            System.out.println("5 Check Balance");
            System.out.println("6 List Accounts");
            System.out.println("7 Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1: // Create Account
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter Balance: ");
                    double bal = sc.nextDouble();

                    accountDAO.createAccount(name, email, bal);

                    // Show all accounts immediately after creation
                    showAccounts();
                    break;

                case 2: // Deposit
                    System.out.print("Enter Account ID: ");
                    int depId = sc.nextInt();
                    System.out.print("Enter Amount: ");
                    double depAmt = sc.nextDouble();
                    transactionDAO.deposit(depId, depAmt);
                    break;

                case 3: // Withdraw
                    System.out.print("Enter Account ID: ");
                    int witId = sc.nextInt();
                    System.out.print("Enter Amount: ");
                    double witAmt = sc.nextDouble();
                    transactionDAO.withdraw(witId, witAmt);
                    break;

                case 4: // Transfer
                    System.out.print("Sender Account ID: ");
                    int sender = sc.nextInt();
                    System.out.print("Receiver Account ID: ");
                    int receiver = sc.nextInt();
                    System.out.print("Enter Amount: ");
                    double transAmt = sc.nextDouble();
                    transactionDAO.transfer(sender, receiver, transAmt);
                    break;

                case 5: // Check Balance
                    System.out.print("Enter Account ID: ");
                    int balId = sc.nextInt();
                    transactionDAO.checkBalance(balId);
                    break;

                case 6: // List Accounts
                    showAccounts();
                    break;

                case 7: // Exit
                    System.out.println("Thank you for using the system!");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Method to list all accounts
    private void showAccounts() throws Exception {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT account_id, name, email, balance FROM accounts";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("\n--- All Accounts ---");
        System.out.printf("%-10s %-15s %-25s %-10s\n", "AccountID", "Name", "Email", "Balance");
        while (rs.next()) {
            System.out.printf("%-10d %-15s %-25s %-10.2f\n",
                    rs.getInt("account_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getDouble("balance"));
        }
    }
}