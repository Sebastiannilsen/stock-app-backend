package ntnu.idata2503.group9.stockappbackend.Controllers;

import ntnu.idata2503.group9.stockappbackend.Models.CandlestickData;
import ntnu.idata2503.group9.stockappbackend.Models.PortfolioHistory;
import ntnu.idata2503.group9.stockappbackend.Repository.PortfolioHistoryRepository;
import org.json.JSONObject;
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
 * Rest controller that controls the endpoints for the portfolio history.
 *
 * @author Gruppe 4
 * @version 1.0
 */
@Controller
@RequestMapping("/api/portfoliohistory")
public class PortfolioHistoryController {

    @Autowired
    PortfolioHistoryRepository portfolioHistoryRepository;

    /**
     * Endpoint that returns all portfolio histories as candleStick
     *
     * @param pid the id of the portfolio that you want to return
     * @return all portfolio histories as a list
     */
    @GetMapping("/portfolios/{pid}")
    public ResponseEntity<List<PortfolioHistory>> getAllPortPortfolioHistoryByPortfolioId(@PathVariable long pid) {
        List<PortfolioHistory> portfolioHistories = this.portfolioHistoryRepository.findByPortfolioPid(pid);
        return ResponseEntity.ok(portfolioHistories);
    }

    /**
     * Endpoint that returns all portfolio histories.
     *
     * @param pid the id of the portfolio that you want to return
     * @return all portfolio histories as a list
     */
    @GetMapping("/portfolios/candleStick/{pid}")
    public ResponseEntity<List<CandlestickData>> getCandlestickDataByPortfolioId(@PathVariable long pid) {
        List<PortfolioHistory> portfolioHistories = this.portfolioHistoryRepository.findByPortfolioPid(pid);

        List<CandlestickData> candlestickDataList = convertToCandlestickData(portfolioHistories);

        return ResponseEntity.ok(candlestickDataList);
    }

    /**
     * Method to convert a portfolioHistories object to candleStick format
     * @param portfolioHistories the portfolio to be converted
     * @return a List with candlestick points
     */
    private List<CandlestickData> convertToCandlestickData(List<PortfolioHistory> portfolioHistories) {
        List<CandlestickData> candlestickDataList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < portfolioHistories.size(); i++) {
            PortfolioHistory current = portfolioHistories.get(i);

            if (i > 0) {
                PortfolioHistory previous = portfolioHistories.get(i - 1);

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


    /**
     * Endpoint that returns the value of a portfolio at a given time.
     *
     * @param pid the id of the portfolio that you want to return
     * @return the value of a portfolio at a given time as monetary change and percentage change
     */
    @GetMapping("/portfolios/values/{pid}")
    public ResponseEntity<Object> getPortfolioHistoricValues(@PathVariable long pid) {
        System.out.println("Endpoint called");
        List<PortfolioHistory> portfolioHistories = this.portfolioHistoryRepository.findByPortfolioPid(pid);

        if (portfolioHistories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        double startPrice = portfolioHistories.get(0).getPrice();
        double endPrice = portfolioHistories.get(portfolioHistories.size() - 1).getPrice();

        double monetaryChange = endPrice - startPrice;
        double percentageChange = (monetaryChange / startPrice) * 100;

        // Creating a JSON object with keys and values
        JSONObject jsonObject = new JSONObject()
                .put("monetaryChange", round(monetaryChange, 2))
                .put("percentageChange", round(percentageChange, 2));

        return ResponseEntity.ok(jsonObject.toString());
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
