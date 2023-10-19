package ntnu.idata2503.group9.stockappbackend.Services;

import ntnu.idata2503.group9.stockappbackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
}
