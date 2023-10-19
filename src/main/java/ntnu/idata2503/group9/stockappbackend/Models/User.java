package ntnu.idata2503.group9.stockappbackend.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private long Uid;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "user")
    @JsonManagedReference(value = "user-list")
    private Set<List> lists = new HashSet<>();

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
     * Return lists.
     * @return lists.
     */
    public Set<List> getLists() {
        return this.lists;
    }

    /**
     * Sets the email of the user
     * @param email email you want to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the password of the user
     * @param password password you want to set
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
