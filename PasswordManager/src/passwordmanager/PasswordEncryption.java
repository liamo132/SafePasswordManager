/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package passwordmanager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author liam
 */
public class PasswordEncryption {
    
 public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
 
  public static boolean verifyPassword(String enteredPassword, String storedHash) {
        String enteredHash = hashPassword(enteredPassword);
        return enteredHash.equals(storedHash);  // true if hashes match
    }
}

