package util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
        static String url="jdbc:mysql://localhost:3306/bankdb";
        static String user="root";
        static String password="mysql123";

        public static Connection getConnection() throws Exception{

            return DriverManager.getConnection(url,user,password);
        }
}
