import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // PostgreSQL database credentials
    private static final String URL = "jdbc:postgresql://localhost:5432/hotel_management";
    // Replace 'HotelDB' with your database name
    private static final String USER = "postgres"; // Replace 'postgres' with your PostgreSQL username
    private static final String PASSWORD = "your_password"; // Replace with your PostgreSQL password

    // Method to get the connection to the database
    public static Connection getConnection() {
        try {
            // Establish and return the connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error connecting to the PostgreSQL database!");
            e.printStackTrace();
            return null;
        }
    }
}
