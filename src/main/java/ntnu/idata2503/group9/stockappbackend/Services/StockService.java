package ntnu.idata2503.group9.stockappbackend.Services;

import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import ntnu.idata2503.group9.stockappbackend.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represent the service class for Stock
 */
@Service
public class StockService {
    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return (List<Stock>) stockRepository.findAll();
    }

    public Stock getStockById(long id) {
        return stockRepository.findById(id).orElse(null);
    }

    public boolean update(long id, Stock stock) {
        boolean success = false;
        if (stockRepository.existsById(id)) {
            stock.setId(id);
            stockRepository.save(stock);
            success = true;
        }
        return success;
    }
}

