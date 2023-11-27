package ntnu.idata2503.group9.stockappbackend.Repository;

import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import ntnu.idata2503.group9.stockappbackend.Models.StockPurchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPurchaseRepository extends CrudRepository<StockPurchase, Long> {
    StockPurchase findByStock(Stock stock);


    @Query(value = "DELETE FROM stock_purchase WHERE spid = ?1", nativeQuery = true)
    void deleteStockPurchaseById(Long stockPurchaseId);

}
