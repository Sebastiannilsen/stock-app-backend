package ntnu.idata2503.group9.stockappbackend.Controllers;

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
import java.util.List;

/**
 * Rest controller that controls the endpoints for the portfolio history.
 *
 * @author Gruppe...
 * @version 1.0
 */
@Controller
@RequestMapping("/api/portfoliohistory")
public class PortfolioHistoryController {

    @Autowired
    PortfolioHistoryRepository portfolioHistoryRepository;

    /**
     * Endpoint that returns all portfolio histories.
     * @param pid the id of the portfolio that you want to return
     * @return all portfolio histories as a list
     */
    @GetMapping("/portfolios/{pid}")
    public ResponseEntity<List<PortfolioHistory>> getAllPortPortfolioHistoryByPortfolioId(@PathVariable long pid) {
        List<PortfolioHistory> portfolioHistories = this.portfolioHistoryRepository.findByPortfolioPid(pid);
        return ResponseEntity.ok(portfolioHistories);
    }

    /**
     * Endpoint that returns the value of a portfolio at a given time.
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
                .put("percentageChange", round(percentageChange,2));

        return ResponseEntity.ok(jsonObject.toString());
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
