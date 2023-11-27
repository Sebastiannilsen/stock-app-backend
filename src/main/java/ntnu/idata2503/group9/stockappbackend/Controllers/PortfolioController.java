package ntnu.idata2503.group9.stockappbackend.Controllers;

import ntnu.idata2503.group9.stockappbackend.Models.Portfolio;
import ntnu.idata2503.group9.stockappbackend.Models.User;
import ntnu.idata2503.group9.stockappbackend.Services.PortfolioService;
import ntnu.idata2503.group9.stockappbackend.Services.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Logger;
import ntnu.idata2503.group9.stockappbackend.Models.Stock;

/**
 * Rest controller that controls the endpoints for the portfolio.
 
 *
 * @author Gruppe...
 * @version 1.0
 */
@Controller
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserService userService;

    private static final String JSONEEXCEPTIONMESSAGE = "The Field(s) in the request is missing or is null";
    private static final String SEVERE = "An error occurred: ";
    private static final Logger LOGGER = Logger.getLogger(PortfolioController.class.getName());

    /**
     * Endpoint that returns all portfolios.
     * @return all portfolios
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Portfolio>> getPortfolios() {
        Iterable<Portfolio> portfolios = this.portfolioService.getAll();
        if (!portfolios.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(portfolios);
    }

    /**
     * Endpoint that returns a portfolio based on the portfolio id
     * @param id the id of the portfolio that you want to return
     * @return the user and HTTP status OK or http status NOT_FOUNd if user was not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioFromId(@PathVariable long id) {
        Portfolio portfolio = this.portfolioService.findById(id);
        if (portfolio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(portfolio);
    }

    /**
     * Endpoint that returns a list of stocks from the portfolio
     * @return list of stocks.
     */
    @GetMapping("/stocks/{uid}")
    public ResponseEntity<List<Stock>> getAllUniqueStocks(@PathVariable int uid) {
        User user = this.userService.findById(uid);
        return ResponseEntity.ok(this.portfolioService.findAllUniqueStocks(user.getUid()));
    }

    /**
     * Endpoint that creates a new portfolio.
     *
     * @param portfolio the body of portfolio that you want to create.
     * @return HTTP status CREATED if created, if not the INTERNAL_SERVER_ERROR.
     * @throws JSONException if an error occurs while creating the portfolio.
     */
    @PostMapping("")
    public ResponseEntity<String> createPortfolio(@RequestBody Portfolio portfolio) {
        try {
            if(!this.portfolioService.add(portfolio)) {
                return new ResponseEntity<>("Portfolio was not added", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Portfolio was added", HttpStatus.CREATED);
        }
        catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return new ResponseEntity<>(JSONEEXCEPTIONMESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint that update a portfolio.
     * @param id the id of the portfolio that you want to update.
     * @param portfolio the new portfolio that you want the old portfolio to be updated to.
     * @return HTTP status OK if updated, if not INTERNAL_SERVER_ERROR.
     * @exception JSONException  if an error occurs while updating the portfolio.
     */
    @PutMapping("{id}")
    public ResponseEntity<String> updatePortfolio(@PathVariable long id, @RequestBody Portfolio portfolio) {
        try {
            Portfolio oldPortfolio = this.portfolioService.findById(id);
            if(oldPortfolio == null) {
                return new ResponseEntity<>("didn't find portfolio", HttpStatus.NOT_FOUND);
            }
            this.portfolioService.update(id,portfolio);
            if(this.portfolioService.findById(id) == null){
                return new ResponseEntity<>("portfolio didn't update", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("portfolio was updated", HttpStatus.OK);
        }
        catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return new ResponseEntity<>(JSONEEXCEPTIONMESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint that deletes a portfolio.
     * @param id the id of the portfolio that you want to delete
     * @return HTTP status OK if deleted, if not INTERNAL_SERVER_ERROR.
     * @exception JSONException  if an error occurs while deleting the portfolio.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePortfolio(@PathVariable long id) {
        try {
            if(!this.portfolioService.delete(id)) {
                return new ResponseEntity<>("Portfolio was not removed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Portfolio was removed", HttpStatus.OK);
        }
        catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return new ResponseEntity<>(JSONEEXCEPTIONMESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

}
