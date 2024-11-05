/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package passwordmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.List; 

/**
 *
 * @author liam
 */

public class PasswordManager {

    private Connection connection;

    public PasswordManager() {
        try {
            String url = "jdbc:mysql://localhost:3306/passwordmanager?useSSL=false";
            String user = "root";
            String password = "Ridgewood29";
            connection = DriverManager.getConnection(url, user, password); // Establish the connection
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception
        }
    }

public void addPassword(String domain, String plainPassword) {
    String sql = "INSERT INTO passwords (domain_name, hashed_password) VALUES (?, ?)";

    try {
        String encryptedPassword = PasswordEncryption.encrypt(plainPassword); // Encrypt the password
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, domain);
            pstmt.setString(2, encryptedPassword);
            pstmt.executeUpdate();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

  public String getPassword(String domain) {
    String sql = "SELECT hashed_password FROM passwords WHERE domain_name = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, domain);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            String encryptedPassword = rs.getString("hashed_password");
            return PasswordEncryption.decrypt(encryptedPassword); // Decrypt the password
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null; // No password found
}
  
  public List<PasswordEntry> getAllPasswords() {
    List<PasswordEntry> passwordList = new ArrayList<>();
    String sql = "SELECT domain_name, hashed_password FROM passwords"; // Adjust the SQL query to fit your table structure

    try (PreparedStatement pstmt = connection.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            String domain = rs.getString("domain_name");
            String hashedPassword = rs.getString("hashed_password");
            passwordList.add(new PasswordEntry(domain, hashedPassword)); // Assuming PasswordEntry has a constructor that takes domain and hashedPassword
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return passwordList; // Return the list of PasswordEntry objects
}
   public int getPasswordCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM passwords"; // Adjust table name as needed

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return count; // Return the actual count
    }
}
    

