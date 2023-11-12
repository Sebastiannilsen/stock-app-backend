package ntnu.idata2503.group9.stockappbackend.Services;

import ntnu.idata2503.group9.stockappbackend.Models.StockHistory;
import ntnu.idata2503.group9.stockappbackend.Repository.StockHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockHistorySevice {

    @Autowired
    StockHistoryRepository stockHistoryRepository;

    public Iterable<StockHistory> getAll() {
        return this.stockHistoryRepository.findAll();
    }

    public List<StockHistory> getAllStockHistoryByStockId(long id) {
        return this.stockHistoryRepository.findByStockId(id);
    }

    public boolean addStockHistory (StockHistory stockHistory) {
        this.stockHistoryRepository.save(stockHistory);
        return true;
    }
}
