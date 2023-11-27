package ntnu.idata2503.group9.stockappbackend.Controllers;

import ntnu.idata2503.group9.stockappbackend.Models.Portfolio;
import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import ntnu.idata2503.group9.stockappbackend.Models.StockPurchase;
import ntnu.idata2503.group9.stockappbackend.Services.PortfolioService;
import ntnu.idata2503.group9.stockappbackend.Services.StockPurchaseService;
import ntnu.idata2503.group9.stockappbackend.Services.StockService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Rest controller that controls the endpoints for the stock purchase.
 * 
 * @author Gruppe 4
 * @version 1.0
 */
@Controller
@RequestMapping("/api/stockpurchease")
public class StockPurcheaseController {

    @Autowired
    StockPurchaseService stockPurchaseService;

    @Autowired
    StockService stockService;

    @Autowired
    PortfolioService portfolioService;

    private static final String JSONEEXCEPTIONMESSAGE = "The Field(s) in the request is missing or is null";
    private static final String SEVERE = "An error occurred: ";
    private static final Logger LOGGER = Logger.getLogger(StockPurcheaseController.class.getName());

    /**
     * Endpoint that returns all stock purchases.
     * 
     * @return all stock purchases
     */
    @GetMapping("")
    public ResponseEntity<List<StockPurchase>> getStockPurchases() {
        Iterable<StockPurchase> stockPurchases = this.stockPurchaseService.getAll();
        if (!stockPurchases.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok((List<StockPurchase>) stockPurchases);
    }

    /**
     * Endpoint that returns a stock purchase based on the stock purchase id
     * 
     * @param id the id of the stock purchase that you want to return
     * @return the stock purchase and HTTP status OK or http status NOT_FOUNd if
     *         stock purchase was not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<StockPurchase> getStockPurcheaseFormId(@PathVariable long id) {
        StockPurchase stockPurchase = this.stockPurchaseService.findById(id);
        if (stockPurchase == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stockPurchase);
    }

    /**
     * Endpoint that updates a stock purchase
     * 
     * @param stockPurchase the stock purchase that you want to update
     * @return the stock purchase and HTTP status OK or http status NOT_FOUNd if
     *         stock purchase was not found
     */
    @PostMapping("")
    public ResponseEntity<String> createStockPurchase(@RequestBody StockPurchase stockPurchase) {
        Portfolio portfolio = this.portfolioService.findById(stockPurchase.getPortfolio().getPid());
        stockPurchase.setPortfolio(portfolio);
        try {
            if (!this.stockPurchaseService.add(stockPurchase)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Stock Purchase was not added");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Stock Purchase was added");
        } catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONEEXCEPTIONMESSAGE);
        }
    }

    /**
     * Endpoint that updates a stock purchase based on the stock purchase id
     * 
     * @param id            the id of the stock purchase that you want to return
     * @param stockPurchase the stock purchase that you want to update
     * @return the stock purchase and HTTP status OK or http status NOT_FOUNd if
     *         stock purchase was not found
     */
    @PutMapping("")
    public ResponseEntity<String> updateStock(@PathVariable long id, @RequestBody StockPurchase stockPurchase) {
        try {
            StockPurchase oldStockPurchase = this.stockPurchaseService.findById(id);
            if (oldStockPurchase == null) {
                return ResponseEntity.notFound().build();
            }
            this.stockPurchaseService.update(id, stockPurchase);
            if (this.stockPurchaseService.findById(id) == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("stockPurchase didn't update");
            }
            return ResponseEntity.ok("stockPurchase was updated");
        } catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONEEXCEPTIONMESSAGE);
        }
    }

    /**
     * Endpoint that deletes a stock purchase based on the stock purchase id
     * 
     * @param id the id of the stock purchase that you want to delete
     * @return HTTP status OK if deleted, if not INTERNAL_SERVER_ERROR.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        try {
            if (!this.stockPurchaseService.delete(id)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("stockPurchase was not removed");
            }
            return ResponseEntity.ok("stockPurchase was removed");
        } catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONEEXCEPTIONMESSAGE);
        }
    }

    /**
     * Endpoint that returns all stock purchases based on the portfolio id
     * 
     * @param id the id of the portfolio that you want to return stock purchases
     *           from
     * @return the stock purchases and HTTP status OK or HTTP status NOT_FOUND if
     *         stock purchases was not found
     */
    @GetMapping("/{id}/stockpurchease")
    public ResponseEntity<StockPurchase> getByStockStockId(@PathVariable long id) {
        Stock stock = this.stockService.getStockById(id);
        StockPurchase stockPurchase = this.stockPurchaseService.findByStock(stock);
        return ResponseEntity.ok(stockPurchase);
    }
}
