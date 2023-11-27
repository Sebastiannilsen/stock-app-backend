package ntnu.idata2503.group9.stockappbackend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a stock
 */
@Entity
@Table(name = "Stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String name;
    private double currentPrice;
    private double openingPrice;
    private double percentChangeIntraday;

    @ManyToMany(mappedBy = "stocks")
    @JsonIgnore
    private Set<List> lists = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "stock_id")
    @JsonManagedReference(value = "stock_stockpurchase")
    private Set<StockPurchase> stockPurchases = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "stock_id")
    @JsonManagedReference(value = "stockhistory-stock")
    private Set<StockHistory> stockHistories = new HashSet<>();

    /**
     * Constructor for a stock
     *
     * @param symbol                id of the stock, e.g. AAPL, TSLA, etc.
     * @param name                  name of the stock
     * @param currentPrice          current price of the stock in NOK
     * @param percentChangeIntraday percent change in price since opening
     */
    public Stock(String symbol, String name, double currentPrice, double percentChangeIntraday) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
        this.openingPrice = currentPrice;
        this.percentChangeIntraday = percentChangeIntraday;
    }

    /**
     * Empty constructor
     */
    public Stock() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getPercentChangeIntraday() {
        return percentChangeIntraday;
    }

    public double getOpeningPrice() {
        return openingPrice;
    }

    public Set<List> getLists() {
        return lists;
    }

    public void setOpeningPrice(double openingPrice) {
        this.openingPrice = openingPrice;
    }

    public void setPercentChangeIntraday(double percentChangeIntraday) {
        this.percentChangeIntraday = percentChangeIntraday;
    }

    public void setLists(Set<List> lists) {
        this.lists = lists;
    }

    public Set<StockPurchase> getStockPurchases() {
        return stockPurchases;
    }

    public void setStockPurchases(Set<StockPurchase> stockPurchases) {
        this.stockPurchases = stockPurchases;
    }

    /**
     * Updates the percent change in price since opening
     * 
     */
    public void updatePercentChangeIntraday() {
        double rawPercentChange = (this.currentPrice - this.openingPrice) / this.openingPrice * 100;
        this.percentChangeIntraday = Math.round(rawPercentChange * 100.0) / 100.0;
    }

    public Set<StockHistory> getStockHistories() {
        return stockHistories;
    }

    public void setStockHistories(Set<StockHistory> stockHistories) {
        this.stockHistories = stockHistories;
    }
}
