package ntnu.idata2503.group9.stockappbackend.Controllers;

import ntnu.idata2503.group9.stockappbackend.Models.CandlestickData;
import ntnu.idata2503.group9.stockappbackend.Models.PortfolioHistory;
import ntnu.idata2503.group9.stockappbackend.Models.StockHistory;
import ntnu.idata2503.group9.stockappbackend.Services.StockHistorySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    /**
     * Endpoint that returns all stock histories.
     *
     * @param pid the id of the stock that you want to get data from
     * @return all portfolio histories as a list
     */
    @GetMapping("/stocks/candleStick/{pid}")
    public ResponseEntity<List<CandlestickData>> getCandlestickDataByStockId(@PathVariable long pid) {
        List<StockHistory> stockHistories = this.stockHistorySevice.getAllStockHistoryByStockId(pid);

        List<CandlestickData> candlestickDataList = convertToCandlestickData(stockHistories);

        return ResponseEntity.ok(candlestickDataList);
    }

    /**
     * Method to convert a portfolioHistories object to candleStick format
     * @param portfolioHistories the portfolio to be converted
     * @return a List with candlestick points
     */
    private List<CandlestickData> convertToCandlestickData(List<StockHistory> portfolioHistories) {
        List<CandlestickData> candlestickDataList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < portfolioHistories.size(); i++) {
            StockHistory current = portfolioHistories.get(i);

            if (i > 0) {
                StockHistory previous = portfolioHistories.get(i - 1);

                // Generate a random offset between 2 and 7
                double offsetLow = (5 * random.nextDouble());
                double offsetHigh = (5 * random.nextDouble());

                // Simulate data where low and high values are different from open and close
                double open = previous.getPrice();
                double close = current.getPrice();
                double low = Math.min(open, close) - offsetLow;
                double high = Math.max(open, close) + offsetHigh;

                CandlestickData candlestickData = new CandlestickData(
                        open,
                        close,
                        round(low,2),
                        round(high,2),
                        current.getDate()
                );

                candlestickDataList.add(candlestickData);
            }
        }

        return candlestickDataList;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
