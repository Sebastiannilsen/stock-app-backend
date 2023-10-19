package ntnu.idata2503.group9.stockappbackend.Models;

import jakarta.persistence.*;

/**
 * Class that represent a user
 *
 * @author Gruppe
 * @version 1.0
 */
@Table (name = "User")
public class User {

    @Id
    @GeneratedValue
    long Uid;
    String email;
    String password;

    /**
     * Constructor for user.
     * @param email the email of the user.
     * @param password the password for the user.
     */
    public User(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    /**
     * Empty constructor that is needed for JPA
     */
    public User () {}

    /**
     * Returns the user id
     * @return uid
     */
    public long getUid() {
        return this.Uid;
    }

    /**
     * returns the email of the user
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Returns the password of the user
     * @return email
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the email of the user
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the password of the user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user is valid
     * @return boolean. True if valid, false if not.
     */
    public boolean isValid() {
        return !" ".equals(this.email) && !" ".equals(this.password);
    }
}
