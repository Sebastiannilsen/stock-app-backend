package ntnu.idata2503.group9.stockappbackend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "app_user")
@Entity
public class User {

    private boolean active = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference(value = "user-list")
    private Set<List> lists = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    /**
     * Empty constructor that is needed for JPA
     */
    public User () {}

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
     * Returns the user id
     * @return uid
     */
    public Long getUid() {
        return this.uid;
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

    public Portfolio getPortfolio(){
        return this.portfolio;
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
     * Sets the portfolio
     * @param portfolio the portfolio you want to set
     */
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    /**
     * Checks if the user is valid
     * @return boolean. True if valid, false if not.
     */
    public boolean isValid() {
        return !" ".equals(this.email) && !" ".equals(this.password);
    }

    /**
     * Checks if the user is active
     * Used for security
     * @return Boolean statement. True if active, false if not
     */
    public boolean isActive() {
        return active;
    }

    public void setLists(Set<List> lists) {
        this.lists = lists;
    }

    /**
     * Sets if the user is active or not.
     * Used for security
     * @param active Set boolean statement. Set true if user is active, set false if not
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
