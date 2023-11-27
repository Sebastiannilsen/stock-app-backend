package ntnu.idata2503.group9.stockappbackend.Controllers;

import ntnu.idata2503.group9.stockappbackend.Models.StockHistory;
import ntnu.idata2503.group9.stockappbackend.Services.StockHistorySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Rest controller that controls the endpoints for the stock history.
 * 
 * @author Gruppe 4
 * @version 1.0
 */
@Controller
@RequestMapping("/api/stockhistory")
public class StockHistoryController {

    @Autowired
    StockHistorySevice stockHistorySevice;

    /**
     * Endpoint that returns all stock histories.
     * @param id the id of the stock that you want to return
     * @return the stock history and HTTP status OK or http status NOT_FOUNd if stock history was not found
     */
    @GetMapping("/stocks/{id}")
    public ResponseEntity<List<StockHistory>> getAllStockHistoryByStockId(@PathVariable long id) {
        List<StockHistory> stockHistories = this.stockHistorySevice.getAllStockHistoryByStockId(id);
        return ResponseEntity.ok(stockHistories);
    }

}
