package ntnu.idata2503.group9.stockappbackend.Repository;

import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import ntnu.idata2503.group9.stockappbackend.Models.StockPurchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPurchaseRepository extends CrudRepository<StockPurchase, Long> {
    StockPurchase findByStock(Stock stock);
}
