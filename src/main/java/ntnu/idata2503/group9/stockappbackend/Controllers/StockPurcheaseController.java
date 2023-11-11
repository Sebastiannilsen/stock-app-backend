package ntnu.idata2503.group9.stockappbackend.Controllers;

import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import ntnu.idata2503.group9.stockappbackend.Models.StockPurchase;
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

@Controller
@RequestMapping("/api/stockpurchease")
public class StockPurcheaseController {

    @Autowired
    StockPurchaseService stockPurchaseService;

    @Autowired
    StockService stockService;

    private static final String JSONEEXCEPTIONMESSAGE = "The Field(s) in the request is missing or is null";
    private static final String SEVERE = "An error occurred: ";
    private static final Logger LOGGER = Logger.getLogger(StockPurcheaseController.class.getName());


    @GetMapping("")
    public ResponseEntity<List<StockPurchase>> getStockPurchases() {
        Iterable<StockPurchase> stockPurchases = this.stockPurchaseService.getAll();
        if(!stockPurchases.iterator().hasNext()) {
            return new ResponseEntity("Didn't find anny stock purchases", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok((List<StockPurchase>) stockPurchases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockPurchase> getStockPurcheaseFormId(@PathVariable long id) {
        StockPurchase stockPurchase = this.stockPurchaseService.findById(id);
        if(stockPurchase == null) {
            return new ResponseEntity("Didn't find stockPurchase", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(stockPurchase);
    }

    @PostMapping("")
    public ResponseEntity<String> createStockPurchase(@RequestBody StockPurchase stockPurchase) {
        try {
            if(!this.stockPurchaseService.add(stockPurchase)) {
                return new ResponseEntity("List was not added", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity("List was added", HttpStatus.CREATED);
        }
        catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return new ResponseEntity(JSONEEXCEPTIONMESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("")
    public ResponseEntity updateStock(@PathVariable long id, @RequestBody StockPurchase  stockPurchase) {
        try {
            StockPurchase oldStockPurchase = this.stockPurchaseService.findById(id);
            if(oldStockPurchase == null) {
                return new ResponseEntity("Didn't find stockPurchase", HttpStatus.NOT_FOUND);
            }
            this.stockPurchaseService.update(id,stockPurchase);
            if(this.stockPurchaseService.findById(id) == null) {
                return new ResponseEntity("stockPurchase didn't update", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity("stockPurchase was updated", HttpStatus.OK);
        }
        catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return new ResponseEntity(JSONEEXCEPTIONMESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        try {
            if(!this.stockPurchaseService.delete(id)) {
                return new ResponseEntity("stockPurchase was not removed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity("stockPurchase was removed", HttpStatus.OK);
        }
        catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return new ResponseEntity(JSONEEXCEPTIONMESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/stockpurchease")
    public ResponseEntity<StockPurchase> getByStockStockId(@PathVariable long id) {
        Stock stock = this.stockService.getStockById(id);
        StockPurchase stockPurchase = this.stockPurchaseService.findByStock(stock);
        return ResponseEntity.ok(stockPurchase);
    }
}
