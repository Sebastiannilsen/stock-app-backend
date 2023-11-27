package ntnu.idata2503.group9.stockappbackend.Services;

import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import ntnu.idata2503.group9.stockappbackend.Models.StockPurchase;
import ntnu.idata2503.group9.stockappbackend.Repository.PortfolioHistoryRepository;
import ntnu.idata2503.group9.stockappbackend.Repository.StockPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.stream.StreamSupport;

/**
 * Represent the service class for StockPurchase
 * Handle the logic of StockPurchase repository.
 *
 * @author Gruppe 4
 * @version 1.0
 */
@Service
public class StockPurchaseService {

    @Autowired
    StockPurchaseRepository stockPurchaseRepository;

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    PortfolioHistoryRepository portfolioHistoryRepository;

    public Iterable<StockPurchase> getAll() {
        return this.stockPurchaseRepository.findAll();
    }

    public StockPurchase findById(long id) {
        return this.stockPurchaseRepository.findById(id).orElse(null);
    }

    private boolean canBeAdded(StockPurchase stockPurchase) {
        return stockPurchase != null && stockPurchase.isValid();
    }

    public boolean add(StockPurchase stockPurchase) {
        boolean added = false;
        if (canBeAdded(stockPurchase)) {
            this.stockPurchaseRepository.save(stockPurchase);
            added = true;
        }
        return added;
    }



    public boolean delete(long portfolioId, long stockId) {
        // check if the user owns the stock
        List<StockPurchase> stockPurchases = StreamSupport.stream(getAll().spliterator(), false)
                .filter(sp -> sp.getPortfolio().getPid() == portfolioId && sp.getStock().getId() == stockId)
                .toList();
        boolean deleted = false;
        if (!stockPurchases.isEmpty()) {
            this.stockPurchaseRepository.deleteStockPurchaseById(stockPurchases.get(0).getSpid());
            deleted = true;
        }
        return deleted;
    }

    public void update(long id, StockPurchase stockPurchase) {
        StockPurchase existingStockPurchase = findById(id);
        String errorMessage = null;
        if (existingStockPurchase == null) {
            errorMessage = "No user exists with the id " + id;
        }
        if (stockPurchase == null || !stockPurchase.isValid()) {
            errorMessage = "Wrong data in request body";
        } else if (stockPurchase.getSpid() != id) {
            errorMessage = "The ID of the user in the URL does not match anny ID in the JSON data";
        }
        if (errorMessage == null) {
            this.stockPurchaseRepository.save(stockPurchase);
        }
    }

    public StockPurchase findByStock(Stock stock) {
        return this.stockPurchaseRepository.findByStock(stock);
    }
}
