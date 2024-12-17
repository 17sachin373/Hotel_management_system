import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnect {
    public static void main(String[] args) {
        // Replace these with your database details
        String url = "jdbc:postgresql://localhost:5432/hotel_management"; // Your database name
        String user = "postgres"; // Your username
        String password = "S@chin098765"; // Your password

        try {
            // Load the PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to the database successfully!");

            // Close the connection
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found. Add it to your project.");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
