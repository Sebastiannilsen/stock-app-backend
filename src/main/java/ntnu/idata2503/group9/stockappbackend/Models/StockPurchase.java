package ntnu.idata2503.group9.stockappbackend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

/**
 * This class represent the Stock Purchase
 *
 * @author gruppe 4
 * @version 1.0
 */

@Entity
@Table(name = "stock_purchase")
public class StockPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spid;

    private Date date = new Date();

    private float price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    @JsonBackReference(value = "stock_stockpurchase")
    private Stock stock;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_pid")
    @JsonBackReference(value = "portfolio_stockpurchase")
    private Portfolio portfolio;

    /**
     * Default constructor.
     * 
     * @param date      the date you want to set.
     * @param price     the price you want to set.
     * @param quantity  the quantity of stock you want to set.
     * @param stock     the type of stock you want to set.
     * @param portfolio the portfolio this purchase belong to.
     */
    public StockPurchase(Date date, float price, int quantity, Stock stock, Portfolio portfolio) {
        setDate(date);
        setPrice(price);
        setQuantity(quantity);
        setStock(stock);
        setPortfolio(portfolio);
    }

    /**
     * Constructor neede for JPA.
     */
    public StockPurchase() {

    }

    public Long getSpid() {
        return spid;
    }

    public Date getDate() {
        return date;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Stock getStock() {
        return stock;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setSpid(Long spid) {
        this.spid = spid;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public boolean isValid() {
        return this.date != null && this.price > 0 && this.stock != null
                && this.portfolio != null && this.quantity > 0;
    }

    @Override
    public String toString(){
        return "StockPurchase{" +
                "spid=" + spid +
                ", date=" + date +
                ", price=" + price +
                ", quantity=" + quantity +
                ", stock=" + stock +
                ", portfolio=" + portfolio +
                '}';
        
    }
}
