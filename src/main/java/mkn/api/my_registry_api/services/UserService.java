package mkn.api.my_registry_api.services;

import jakarta.transaction.Transactional;
import mkn.api.my_registry_api.entities.User;
import mkn.api.my_registry_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id)); //!-1
    }

    public User findByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public User insert(User user) {
        return repository.save(user);
    }

    public void deleteByEmail(String email) {
       User userToDelete = findByEmail(email);
       repository.deleteById(userToDelete.getId());

    }

    public void delete(long id) {
        repository.deleteById(id);
    }


}
