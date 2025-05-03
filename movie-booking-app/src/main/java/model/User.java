/**
 * 
 */

package model;

import java.time.LocalDate;

/**
 * 
 */

public class User {
    private int id;
    private String email;
    private String password;
    private LocalDate birthdate;
    private boolean isAdmin;

    public User(int id, String email, String password, LocalDate birthdate, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.isAdmin = isAdmin;
    }

    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public LocalDate getBirthdate() { return birthdate; }
    public boolean isAdmin() { return isAdmin; }
}