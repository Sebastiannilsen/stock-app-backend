package ntnu.idata2503.group9.stockappbackend.Repository;
import ntnu.idata2503.group9.stockappbackend.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
