package ntnu.idata2503.group9.stockappbackend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Pid;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "portfolio_id")
    @JsonManagedReference(value = "portfolio_stockpurchase")
    private Set<StockPurchase> stockPurchases = new HashSet<>();


    /**
     * Constructor for portfolio.
     * @param user the user you want to set that owns the portfolio.
     */
    public Portfolio(User user) {
        setUser(user);
    }

    /**
     * Constructor needed for JPA
     */
    public Portfolio() {}

    /**
     * Sets the user.
     * @param user the user yo want to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Return user.
     * @return user.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Returns portfolio id.
     * @return pid.
     */
    public long getPid() {
        return this.Pid;
    }

    public void setPid(long pid) {
        Pid = pid;
    }

    public Set<StockPurchase> getStockPurchases() {
        return stockPurchases;
    }

    public void setStockPurchases(Set<StockPurchase> stockPurchases) {
        this.stockPurchases = stockPurchases;
    }

    /**
     * Checks if the portfolio is valid
     * @return boolean statement. True if portfolio is valid, false if not.
     */
    public boolean isValid() {
        return this.user != null;
    }

}
