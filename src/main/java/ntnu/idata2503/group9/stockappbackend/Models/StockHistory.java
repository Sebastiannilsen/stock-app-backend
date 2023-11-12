package ntnu.idata2503.group9.stockappbackend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ntnu.idata2503.group9.stockappbackend.Models.Stock;

@Entity
@Table(name = "stock_history")
public class StockHistory {

    @Id
    @GeneratedValue
    long shid;

    private int price;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    @JsonBackReference(value = "stockhistory-stock")
    private Stock stock;

    public StockHistory(int price, Date date, Stock stock) {
        setPrice(price);
        setDate(date);
        setStock(stock);
    }

    public StockHistory() {

    }

    public long getShid() {
        return shid;
    }

    public int getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public Stock getStock() {
        return stock;
    }

    public void setShid(long shid) {
        this.shid = shid;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
