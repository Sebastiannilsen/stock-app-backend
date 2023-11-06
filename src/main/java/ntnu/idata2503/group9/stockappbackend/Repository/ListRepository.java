package ntnu.idata2503.group9.stockappbackend.Repository;

import ntnu.idata2503.group9.stockappbackend.Models.List;
import ntnu.idata2503.group9.stockappbackend.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ListRepository extends CrudRepository<List, Long> {
    java.util.List<List> findAllByUser(User user);
}
