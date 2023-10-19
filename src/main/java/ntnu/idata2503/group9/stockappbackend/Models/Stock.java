package ntnu.idata2503.group9.stockappbackend.Models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a stock
 */
@Entity

public class Stock {
    @Id
    private String symbol;
    private String name;
    private double price;
    private double percentChangeIntraday;
    
    @ManyToMany(mappedBy = "list")
    private final Set<List> lists = new HashSet<>();

    /**
     * Constructor for a stock
     * @param symbol id of the stock, e.g. AAPL, TSLA, etc.
     * @param name name of the stock
     * @param price current price of the stock in NOK
     * @param percentChangeIntraday percent change in price since opening
     */
    public Stock(String symbol, String name, double price, double percentChangeIntraday) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.percentChangeIntraday = percentChangeIntraday;
    }

    /**
     * Empty constructor
     */
    public Stock() {

    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercentChangeIntraday() {
        return percentChangeIntraday;
    }

    public void setPercentChangeIntraday(double percentChangeIntraday) {
        this.percentChangeIntraday = percentChangeIntraday;
    }
}
