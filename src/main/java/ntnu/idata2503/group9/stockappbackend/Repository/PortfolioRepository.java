package ntnu.idata2503.group9.stockappbackend.Repository;

import ntnu.idata2503.group9.stockappbackend.Models.Portfolio;
import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
    @Query("SELECT DISTINCT sp.stock FROM User u " +
            "JOIN u.portfolio p " +
            "JOIN p.stockPurchases sp " +
            "WHERE u.uid = :uid")
    List<Stock> findUniqueStocksByUid(Long uid);
}
