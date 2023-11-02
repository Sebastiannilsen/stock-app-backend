package ntnu.idata2503.group9.stockappbackend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that represent a list.
 *
 * @author Gruppe...
 * @version 1.0
 */
@Table(name = "Lists")
@Entity
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lid;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "list_stock",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_symbol")
    )
    private Set<Stock> stocks = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Constructor for list.
     * @param name the name of the list.
     */
    public List(String name, User user) {
        setName(name);
        setUser(user);
    }

    /**
     * Empty constructor that is needed for JPA
     */
    public List() {}

    /**
     * Sets the list name.
     * @param name the name you want for the list.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set user.
     * @param user the user you want to set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the list id.
     * @return lid.
     */
    public long getLid() {
        return this.lid;
    }

    /**
     * Returns the list name.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns stocks
     * @return stocks
     */
    public Set<Stock> getStocks() {
        return this.stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    /**
     * Return user.
     * @return user.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Checks if the list is valid.
     * @return Boolean statement. True if the list is valid, false if not.
     */
    public boolean isValid() {
        return !" ".equals(this.name);
    }
}
