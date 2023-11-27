package ntnu.idata2503.group9.stockappbackend.Services;

import ntnu.idata2503.group9.stockappbackend.Models.User;
import ntnu.idata2503.group9.stockappbackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Represent the service class for user
 * Handle the logic of user repository.
 *
 * @author Gruppe 4
 * @version 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Returns all users.
     * @return all users
     */
    public Iterable<User> getAll() {
        return this.userRepository.findAll();
    }

    /**
     * Return a user by id.
     * @param id of the user you want to return.
     * @return user.
     */
    public User findById(long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    /**
     * Returns user by email.
     * @param email the email of the user you want to return.
     * @return user.
     */
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    /**
     * Checks if the user can be added to the repository.
     * @param user the user you want to check if it can be added,
     * @return boolean statement. True if it can be added, fales if not.
     */
    private boolean canBeAdded(User user) {
        return user != null && user.isValid();
    }

    /**
     * Added a user to the user repository.
     * @param user the user you want to add
     * @return boolean statement. True if added, false if not.
     */
    public boolean add(User user) {
        boolean added = false;
        if(canBeAdded(user)) {
            this.userRepository.save(user);
            added = true;
        }
        return added;
    }

    /**
     * Removes a user form the repository.
     * @param id the id of the user that you want to remove
     * @return boolean statement. True if removed, false if not.
     */
    public boolean delete(long id) {
        boolean deleted = false;
        if(findById(id) != null) {
            this.userRepository.deleteById(id);
            deleted = true;
        }
        return deleted;
    }

    /**
     * Updates a user in the repository.
     * @param id the id of the user that you want to update.
     * @param user the user you want to set the old user to.
     */
    public void update(long id, User user) {
        User existingUser = findById(id);
        String errorMessage = null;
        if (existingUser == null) {
            errorMessage = "No user exists with the id " + id;
        }
        if (user == null || !user.isValid()) {
            errorMessage = "Wrong data in request body";
        }
        else if(user.getUid() != id) {
            errorMessage = "The ID of the user in the URL does not match anny ID in the JSON data";
        }
        if (errorMessage == null) {
            this.userRepository.save(user);
        }
    }
}
