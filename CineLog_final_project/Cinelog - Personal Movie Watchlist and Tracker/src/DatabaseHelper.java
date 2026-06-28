import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cinelog", "root", "Muneeb51431384963");
            }
        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
        return conn;
    }
}
