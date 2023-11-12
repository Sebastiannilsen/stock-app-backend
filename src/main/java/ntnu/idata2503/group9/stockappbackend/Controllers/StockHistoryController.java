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

@Controller
@RequestMapping("/api/stockhistory")
public class StockHistoryController {

    @Autowired
    StockHistorySevice stockHistorySevice;

    @GetMapping("/stocks/{id}")
    public ResponseEntity<List<StockHistory>> getAllStockHistoryByStockId(@PathVariable long id) {
        List<StockHistory> stockHistories = this.stockHistorySevice.getAllStockHistoryByStockId(id);
        return ResponseEntity.ok(stockHistories);
    }

}
