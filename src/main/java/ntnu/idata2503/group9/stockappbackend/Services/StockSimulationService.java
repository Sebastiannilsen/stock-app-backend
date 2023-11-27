package ntnu.idata2503.group9.stockappbackend.Services;

import ntnu.idata2503.group9.stockappbackend.Models.*;
import ntnu.idata2503.group9.stockappbackend.Repository.PortfolioHistoryRepository;
import ntnu.idata2503.group9.stockappbackend.Repository.PortfolioRepository;
import ntnu.idata2503.group9.stockappbackend.Repository.StockHistoryRepository;
import ntnu.idata2503.group9.stockappbackend.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

/**
 * Service class for simulating stock prices.
 * This class is responsible for simulating stock prices and updating the
 * database.
 * 
 * @author Grupe...
 * @version 1.0
 */
@Service
@EnableScheduling
public class StockSimulationService {

    private final StockRepository stockRepository;
    private final Random random = new Random();

    @Autowired
    public StockSimulationService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
        startSimulation();
    }

    @Autowired
    StockHistoryRepository stockHistoryRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    PortfolioHistoryRepository portfolioHistoryRepository;

    /**
     * Starts the stock simulation.
     */
    public void startSimulation() {
        // Add 30 different stocks (you can customize this as per your requirements)
        addInitialStocks();

        // Schedule price updates every 10 seconds
        //scheduler.scheduleAtFixedRate(this::updateStockPrices, 0, 10, TimeUnit.SECONDS);
    }

    /**
     * Adds dummy stocks to the database if the database is empty.
     */
    private void addInitialStocks() {
        // You can create and save 30 different stocks with initial prices here.
        // For the sake of brevity, let's assume you have predefined stock data.
        // Example: Stock(stockSymbol, stockName, initialPrice)
        Stock stock1 = new Stock("EQNR", "Equinor", 369.05, -1.47);
        Stock stock2 = new Stock("NAS", "Norwegian Air Shuttle", 9.052, +11.43);
        Stock stock3 = new Stock("DNB", "DNB Bank ASA", 197.4, -0.93);
        Stock stock4 = new Stock("FRO", "Frontline PLC", 252.85, +0.67);
        Stock stock5 = new Stock("TEL", "Telenor ASA", 167.1, -0.24);
        Stock stock6 = new Stock("ORK", "Orkla ASA", 86.9, +0.12);
        Stock stock7 = new Stock("STL", "Statoil ASA", 164.1, -0.24);
        Stock stock8 = new Stock("TGS", "TGS-NOPEC Geophysical Company ASA", 141.1, +0.14);
        Stock stock9 = new Stock("AKER", "Aker ASA", 294.1, +0.17);
        Stock stock10 = new Stock("NHY", "Norsk Hydro ASA", 44.1, -0.24);
        Stock stock11 = new Stock("SUBC", "Subsea 7 SA", 104.1, +0.24);
        Stock stock12 = new Stock("MHG", "Marine Harvest ASA", 164.1, -0.24);
        Stock stock13 = new Stock("NAS", "Norwegian Air Shuttle ASA", 164.1, +0.24);
        Stock stock14 = new Stock("AKERBP", "Aker BP ASA", 164.1, -0.24);
        Stock stock15 = new Stock("YAR", "Yara International ASA", 164.1, +0.24);
        Stock stock16 = new Stock("SCHA", "Schibsted ASA", 164.1, -0.24);
        Stock stock17 = new Stock("PGS", "PGS ASA", 164.1, +0.24);
        Stock stock18 = new Stock("OTL", "Odfjell Technology LTD", 164.1, -0.24);
        Stock stock19 = new Stock("SALM", "SalMar ASA", 164.1, +0.24);
        Stock stock20 = new Stock("GJF", "Gjensidige Forsikring ASA", 164.1, -0.24);
        // ... add more stocks
        if (!stockRepository.findAll().iterator().hasNext()) {
            stockRepository.saveAll(List.of(stock1, stock2, stock3, stock4, stock5, stock6, stock7, stock8, stock9,
                    stock10, stock11, stock12, stock13, stock14, stock15, stock16, stock17, stock18, stock19, stock20));
        }

    }

    /**
     * Changes all the stock prices in the database every 10 seconds.
     * The price can go up or down by a random percentage between -0.4% and 0.4%.
     */
    @Scheduled(fixedRate = 10000)
    private void updateStockPrices() {
        List<Stock> stocks = (List<Stock>) stockRepository.findAll();

        for (Stock stock : stocks) {
            double currentPrice = stock.getCurrentPrice();
            double randomFactor = (random.nextDouble() * 0.008) - 0.004; // Random value between -0.4% and 0.4%
            double priceChange = currentPrice * randomFactor;
            double newPrice = currentPrice + priceChange;

            // Ensure the new price is not negative or too low
            double minimumPrice = 1.0; // Set your desired minimum price
            if (newPrice < minimumPrice) {
                newPrice = minimumPrice;
            }
            double newRoundedPrice = Math.round(newPrice * 100.0) / 100.0;
            stock.setCurrentPrice(newRoundedPrice);
            stock.updatePercentChangeIntraday();
            stockRepository.save(stock);
        }
    }

    /**
     * Updates the stock history every 10 minutes.
     */
    @Scheduled(fixedRate = 600000)
    private void updateStockHistory() {
        List<Stock> stocks = (List<Stock>) stockRepository.findAll();
        for (Stock stock : stocks) {
            StockHistory stockHistory = new StockHistory(stock.getCurrentPrice(), new Date(), stock);
            stockHistoryRepository.save(stockHistory);
        }
    }

    /**
     * Updates the portfolio history every 10 minutes.
     */
    @Scheduled(fixedRate = 600000)
    private void updatePortfolioHistory() {
        List<Portfolio> portfolios = (List<Portfolio>) this.portfolioRepository.findAll();
        for (Portfolio portfolio : portfolios) {
            List<Stock> stocks = this.portfolioRepository.findUniqueStocksByUid(portfolio.getUser().getUid());
            double price = 0;
            for (Stock stock : stocks) {
                price = price + stock.getCurrentPrice();
            }

            price = round(price, 2);
            PortfolioHistory portfolioHistory = new PortfolioHistory(price, new Date(), portfolio);
            this.portfolioHistoryRepository.save(portfolioHistory);
        }
    }

    /**
     * Rounds a double to a given number of decimal places.
     * @param value the value to round
     * @param places the number of decimal places
     * @return the rounded value
     */
    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
