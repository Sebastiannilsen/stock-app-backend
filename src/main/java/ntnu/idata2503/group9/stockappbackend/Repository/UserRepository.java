package ntnu.idata2503.group9.stockappbackend.Repository;
import ntnu.idata2503.group9.stockappbackend.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
