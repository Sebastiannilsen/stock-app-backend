package ntnu.idata2503.group9.stockappbackend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.Date;

@Entity
@Table(name = "portfoliohistory")
public class PortfolioHistory {

    @Id
    @GeneratedValue
    private long phid;
    private double price;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "portfolio_pid")
    @JsonBackReference(value = "portfoliohistory-portfolio")
    private Portfolio portfolio;

    public PortfolioHistory(double price, Date date, Portfolio portfolio) {
        setPrice(price);
        setDate(date);
        setPortfolio(portfolio);
    }

    public PortfolioHistory() {}

    public Long getPhid() {
        return phid;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPhid(Long phid) {
        this.phid = phid;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
