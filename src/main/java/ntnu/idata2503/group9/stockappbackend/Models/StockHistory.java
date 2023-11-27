package ntnu.idata2503.group9.stockappbackend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Date;

/**
 * Class that represent a stock history.
 * 
 * @author Gruppe...
 * @version 1.0
 */
@Entity
@Table(name = "stock_history")
public class StockHistory {

    @Id
    @GeneratedValue
    private Long shid;

    private double price;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    @JsonBackReference(value = "stockhistory-stock")
    private Stock stock;

    public StockHistory(double price, Date date, Stock stock) {
        setPrice(price);
        setDate(date);
        setStock(stock);
    }

    public StockHistory() {

    }

    public Long getShid() {
        return shid;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public Stock getStock() {
        return stock;
    }

    public void setShid(Long shid) {
        this.shid = shid;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
