package ntnu.idata2503.group9.stockappbackend.Repository;

import ntnu.idata2503.group9.stockappbackend.Models.PortfolioHistory;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

@Repository
public interface PortfolioHistoryRepository extends CrudRepository<PortfolioHistory, Long> {
    List<PortfolioHistory> findByPortfolioPid(Long pid);

}
