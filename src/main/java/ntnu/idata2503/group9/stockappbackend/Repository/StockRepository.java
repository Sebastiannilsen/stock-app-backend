package ntnu.idata2503.group9.stockappbackend.Repository;

import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface StockRepository extends CrudRepository<Stock, Long> {
    List<Stock> findStocksByListsLid(long lid);
}
