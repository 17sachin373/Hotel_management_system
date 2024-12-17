import java.sql.*;
import java.util.Scanner;

public class HotelManagementSystem {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Management System");
        while (true) {
            System.out.println("\n1. Add Room");
            System.out.println("2. Add Customer");
            System.out.println("3. Book a Room");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addRoom();
                case 2 -> addCustomer();
                case 3 -> bookRoom();
                case 4 -> viewBookings();
                case 5 -> {
                    System.out.println("Exiting... Thank you!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addRoom() {
        System.out.print("Enter room type: ");
        String type = scanner.nextLine();
        System.out.print("Enter price per night: ");
        double price = scanner.nextDouble();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Rooms (room_type, price_per_night) VALUES (?, ?)")) {
            stmt.setString(1, type);
            stmt.setDouble(2, price);
            stmt.executeUpdate();
            System.out.println("Room added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addCustomer() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter ID proof: ");
        String idProof = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Customers (name, phone, id_proof) VALUES (?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, idProof);
            stmt.executeUpdate();
            System.out.println("Customer added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void bookRoom() {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        System.out.print("Enter room ID: ");
        int roomId = scanner.nextInt();
        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkIn = scanner.next();
        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOut = scanner.next();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Bookings (customer_id, room_id, check_in_date, check_out_date, total_price) " +
                     "VALUES (?, ?, ?, ?, DATEDIFF(?, ?) * (SELECT price_per_night FROM Rooms WHERE room_id = ?))")) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, roomId);
            stmt.setString(3, checkIn);
            stmt.setString(4, checkOut);
            stmt.setString(5, checkOut);
            stmt.setString(6, checkIn);
            stmt.setInt(7, roomId);
            stmt.executeUpdate();
            System.out.println("Room booked successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewBookings() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Bookings")) {
            System.out.printf("%-10s %-15s %-10s %-12s %-12s %-10s%n", "Booking ID", "Customer ID", "Room ID", "Check-In", "Check-Out", "Total Price");
            while (rs.next()) {
                System.out.printf("%-10d %-15d %-10d %-12s %-12s %-10.2f%n",
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("room_id"),
                        rs.getDate("check_in_date"),
                        rs.getDate("check_out_date"),
                        rs.getDouble("total_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
