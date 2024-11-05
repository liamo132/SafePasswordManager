/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package passwordmanager;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

/**
 *
 * @author liam
 */

public class PasswordEncryption {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static SecretKey secretKey;

    // File to store the key (you can also use a keystore)
    private static final String KEY_FILE = "secret.key";

    static {
        try {
            // Load the key from a file or generate a new one
            File keyFile = new File(KEY_FILE);
            if (keyFile.exists()) {
                // Load the key from the file
                byte[] keyBytes = new byte[16]; // 128-bit key
                try (FileInputStream fis = new FileInputStream(keyFile)) {
                    fis.read(keyBytes);
                }
                secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
            } else {
                // Generate a new key and save it to the file
                KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
                keyGenerator.init(128);
                secretKey = keyGenerator.generateKey();

                // Save the key to a file
                try (FileOutputStream fos = new FileOutputStream(keyFile)) {
                    fos.write(secretKey.getEncoded());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String password) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedPassword) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }
}