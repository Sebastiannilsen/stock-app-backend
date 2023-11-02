package ntnu.idata2503.group9.stockappbackend.Services;

import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import ntnu.idata2503.group9.stockappbackend.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class StockSimulationService {

    private final StockRepository stockRepository;
    private final ScheduledExecutorService scheduler;
    private final Random random = new Random();

    @Autowired
    public StockSimulationService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void startSimulation() {
        // Add 30 different stocks (you can customize this as per your requirements)
        addInitialStocks();

        // Schedule price updates every 10 seconds
        ScheduledFuture<?> priceUpdateHandle = scheduler.scheduleAtFixedRate(this::updateStockPrices, 0, 10, TimeUnit.SECONDS);
    }

    private void addInitialStocks() {
        // You can create and save 30 different stocks with initial prices here.
        // For the sake of brevity, let's assume you have predefined stock data.
        // Example: Stock(stockSymbol, stockName, initialPrice)
        Stock stock1 = new Stock("AAPL", "Apple Inc.", 150.0, 0);
        Stock stock2 = new Stock("GOOGL", "Alphabet Inc.", 2500.0, 0);
        // ... add more stocks
        stockRepository.saveAll(List.of(stock1, stock2 /*, ... */));
    }

    private void updateStockPrices() {
        List<Stock> stocks = (List<Stock>) stockRepository.findAll();

        for (Stock stock : stocks) {
            double currentPrice = stock.getPrice();
            double percentageChange = (random.nextDouble() * 0.004) + 0.001; // 0.1% to 0.4% change
            double priceChange = currentPrice * percentageChange;
            double newPrice = currentPrice + priceChange;
            stock.setPrice(newPrice);
            stockRepository.save(stock);
        }
    }
}
