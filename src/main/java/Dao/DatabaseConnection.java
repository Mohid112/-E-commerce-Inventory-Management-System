package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/managementdb";
    private static final String USER = "root";
    private static final String PASSWORD = "abc123";
    private static Connection connection = null;




    public static synchronized Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                // ✅ Ensure auto-commit is enabled so writes work immediately
                connection.setAutoCommit(true);

                System.out.println("✅ Database Connected Successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ JDBC Driver Not Found!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("❌ Database Connection Failed!");
                e.printStackTrace();
            }
        }
        return connection;
    }

}
