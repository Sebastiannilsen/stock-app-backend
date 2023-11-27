package ntnu.idata2503.group9.stockappbackend.Controllers;

import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import ntnu.idata2503.group9.stockappbackend.Repository.StockRepository;
import ntnu.idata2503.group9.stockappbackend.Services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller that controls the endpoints for the stock.
 * 
 * @author Gruppe...
 * @version 1.0
 */
@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private final StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Endpoint that returns all stocks.
     * @return all stocks
     */
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    /**
     * Endpoint that returns a stock based on the stock id
     * @param id the id of the stock that you want to return
     * @return the stock and HTTP status OK or http status NOT_FOUNd if stock was not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable long id) {
        Stock stock = stockService.getStockById(id);
        if (stock != null) {
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint that updates a stock based on the stock id
     * @param id the id of the stock that you want to return
     * @param stock the stock that you want to update
     * @return the stock and HTTP status OK or http status NOT_FOUNd if stock was not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable long id, @RequestBody Stock stock) {
        if (stockService.update(id, stock)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint that returns all stocks based on the list id
     * @param lid the id of the list that you want to return stocks from
     * @return the stocks and HTTP status OK or HTTP status NOT_FOUND if stocks was not found
     */
    @GetMapping("/lists/{lid}/stocks")
    public ResponseEntity<List<Stock>> getAllStocksByListsLid(@PathVariable(value = "lid") long lid) {
        List<Stock> stocks = this.stockRepository.findStocksByListsLid(lid);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

}
