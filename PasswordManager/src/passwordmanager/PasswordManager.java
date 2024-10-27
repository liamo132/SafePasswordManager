/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package passwordmanager;

import com.sun.jdi.connect.spi.Connection;

/**
 *
 * @author User
 */


import java.sql.Connection; // Correct import
import java.sql.PreparedStatement; // Import for PreparedStatement
import java.sql.SQLException; // Import for SQLException

public class PasswordManager {

    public static void main(String[] args) {
        // Here you can call the method or create a GUI to trigger it
        PasswordManager pm = new PasswordManager();
        pm.addPassword("example.com", "mypassword"); // Example call
    }

    public void addPassword(String domain, String password) {
        String sql = "INSERT INTO passwords (domain, password) VALUES (?, ?)";
        try (Connection conn = Database.connect(); // Use the Database.connect() method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, domain);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("Password saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}