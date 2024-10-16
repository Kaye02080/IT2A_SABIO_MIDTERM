package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Student {

    static Scanner sc = new Scanner(System.in);

    // Connect to the SQLite database
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:Student.db"); // Establish connection
            System.out.println("Connection Successful");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
        return con;
    }

    public static void main(String[] args) {
        while (true) {
            Choices();
        }
    }

    // Add a new student
    public static void addStudents() {
        System.out.print("Student First Name: ");
        String fname = sc.next();
        System.out.print("Student Last Name: ");
        String lname = sc.next();
        System.out.print("Student Age: ");
        String age = sc.next();
        System.out.print("Student Email: ");
        String email = sc.next();
        System.out.print("Student Address: ");
        String address = sc.next();
        System.out.print("Student Phone Number: ");
        String phonenum = sc.next();

        String sql = "INSERT INTO Student (s_fname, s_lname, s_age, s_email, s_address, s_phonenum) VALUES (?, ?, ?, ?, ?, ?)";
        addRecord(sql, fname, lname, age, email, address, phonenum);
    }

    // Execute the add record SQL statement
    public static void addRecord(String sql, String... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setString(i + 1, values[i]);
            }

            pstmt.executeUpdate();
            System.out.println("Record added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }

    // Update an existing student's details
    public static void updateStudents() {
        System.out.print("Enter Student ID to update: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.print("Updated First Name: ");
        String fname = sc.next();
        System.out.print("Updated Last Name: ");
        String lname = sc.next();
        System.out.print("Updated Age: ");
        String age = sc.next();
        System.out.print("Updated Email: ");
        String email = sc.next();
        System.out.print("Updated address: ");
        String address = sc.next();
        System.out.print("Updated Phone Number: ");
        String phonenum = sc.next();

        String sql = "UPDATE Student SET s_fname = ?, s_lname = ?, s_age = ?, s_email = ?, s_address = ?, s_phonenum = ? WHERE s_id = ?";
        updateRecord(sql, fname, lname, age, email, address, phonenum, id);
    }

    
    public static void updateRecord(String sql, String fname, String lname, String age, String email, String address, String phonenum, int id) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setString(3, age);
            pstmt.setString(4, email);
            pstmt.setString(5, address);
            pstmt.setString(6, phonenum);
            pstmt.setInt(7, id); 

            pstmt.executeUpdate();
            System.out.println("Record updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }

    
    public static void deleteStudents() {
        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine(); 

        String sql = "DELETE FROM Student WHERE s_id = ?";
        deleteRecord(sql, id);
    }

    
    public static void deleteRecord(String sql, int id) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Record deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
    }

    
    public static void viewStudents() {
        String sql = "SELECT * FROM Student";
        viewRecords(sql);
    }

    
    public static void viewRecords(String sql) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("s_id");
                String fname = rs.getString("s_fname");
                String lname = rs.getString("s_lname");
                String age = rs.getString("s_age");
                String email = rs.getString("s_email");
                String address = rs.getString("s_address");
                String phonenum = rs.getString("s_phonenum");
                System.out.println("ID: " + id + ", Name: " + fname + " " + lname + ", Age: " + age + ", Email: " + email + ", Address: " + address + ", Phone Number: " + phonenum);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }

    // Display options for user to choose from
    public static void Choices() {
        System.out.println("1. ADD");
        System.out.println("2. UPDATE");
        System.out.println("3. DELETE");
        System.out.println("4. VIEW");
        System.out.println("5. EXIT");
        System.out.print("Choose an option: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                addStudents();
                break;
            case 2:
                updateStudents();
                break;
            case 3:
                deleteStudents();
                break;
            case 4:
                viewStudents();
                break;
            case 5:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please choose a valid option.");
        }
    }
}
