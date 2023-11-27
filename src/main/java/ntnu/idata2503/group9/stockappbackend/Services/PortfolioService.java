package ntnu.idata2503.group9.stockappbackend.Services;

import ntnu.idata2503.group9.stockappbackend.Models.Portfolio;
import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import ntnu.idata2503.group9.stockappbackend.Models.User;
import ntnu.idata2503.group9.stockappbackend.Repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class represent the service clas for portfolio
 * Handle the logic of the portfolio repository
 *
 * @author gruppe 4
 * @version 1.0
 */
@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserService userService;

    /**
     * Returns all portfolio.
     * @return all portfolio.
     */
    public Iterable<Portfolio> getAll(){
        return this.portfolioRepository.findAll();
    }

    /**
     * Returns a portfolio based on id.
     * @param id the id of the portfolio you want to find.
     * @return portfolio.
     */
    public Portfolio findById(long id) {
        return this.portfolioRepository.findById(id).orElse(null);
    }

    /**
     * Checks if the portfolio can be added.
     * @param portfolio the portfolio that you want to check.
     * @return boolean statement. True if it can be added, false if not.
     */
    private boolean canBeAdded(Portfolio portfolio) {
        return portfolio != null && portfolio.isValid();
    }

    /**
     * Added a portfolio to the repository.
     * @param portfolio the portfolio that you want to add
     * @return boolean statement. True if it was added, false if not.
     */
    public boolean add(Portfolio portfolio) {
        boolean added = false;
        if(canBeAdded(portfolio)) {
            this.portfolioRepository.save(portfolio);
            added = true;
        }
        return added;
    }

    /**
     * Removes a portfolio form the repository.
     * @param id the id of the portfolio that you want to remove.
     * @return boolean statement. True if added, false if not.
     */
    public boolean delete(long id) {
        boolean deleted = false;
        if(findById(id) != null){
            this.portfolioRepository.deleteById(id);
            deleted = true;
        }
        return deleted;
    }

    /**
     * Update a portfolio in the repository.
     * @param id the id of the portfolio that you want to update
     * @param portfolio the new portfolio you want the portfolio to be updated to.
     */
    public void update(long id, Portfolio portfolio) {
        Portfolio existingPortfolio = findById(id);
        String errorMessage = null;
        if (existingPortfolio == null) {
            errorMessage = "No portfolio exists with the id " + id;
        }
        if (portfolio == null || !portfolio.isValid()) {
            errorMessage = "Wrong data in request body";
        }
        else if(portfolio.getPid() != id) {
            errorMessage = "The ID of the portfolio in the URL does not match anny ID in the JSON data";
        }
        if (errorMessage == null) {
            this.portfolioRepository.save(portfolio);
        }
    }

    /**
     * Retuns all the stocks that belong to a portfolio
     * If one stock have been purchase more then one time, it only returns the stock ones.
     * @return stocks
     */
    public List<Stock> findAllUniqueStocks(long uid) {
        User user = this.userService.findById(uid);
        return this.portfolioRepository.findUniqueStocksByUid(user.getUid());
    }
}
