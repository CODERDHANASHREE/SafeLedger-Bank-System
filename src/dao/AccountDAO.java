package dao;

import util.DBConnection;
import java.sql.*;

public class AccountDAO {

    public void createAccount(String name, String email, double balance) throws Exception {

        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO accounts(name,email,balance) VALUES(?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, name);
        ps.setString(2, email);
        ps.setDouble(3, balance);

        ps.executeUpdate();

        // Get the auto-generated account ID
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()){
            int id = rs.getInt(1);
            System.out.println("Account created. Your account ID is: " + id);
        }

        ps.close();
        con.close();
    }
}