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

    @GetMapping("/lists/{lid}/stocks")
    public ResponseEntity<List<Stock>> getAllStocksByListsLid(@PathVariable(value = "lid")long lid) {
        List<Stock> stocks = this.stockRepository.findStocksByListsLid(lid);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

}

