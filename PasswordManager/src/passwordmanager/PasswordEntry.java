/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package passwordmanager;

/**
 *
 * @author User
 */

public class PasswordEntry {

    private String domain;
    private String hashedPassword;

    public PasswordEntry(String domain, String hashedPassword) {
        this.domain = domain;
        this.hashedPassword = hashedPassword;
    }

    public String getDomain() {
        return domain;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

}
