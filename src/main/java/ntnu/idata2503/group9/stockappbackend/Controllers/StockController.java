package ntnu.idata2503.group9.stockappbackend.Controllers;

import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import ntnu.idata2503.group9.stockappbackend.Services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller that controls the endpoints for the stock.
 */
@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable long id) {
        Stock stock = stockService.getStockById(id);
        if (stock != null) {
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable long id, @RequestBody Stock stock) {
        if (stockService.update(id, stock)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

