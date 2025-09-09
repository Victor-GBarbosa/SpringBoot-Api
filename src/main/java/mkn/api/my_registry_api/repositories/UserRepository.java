package mkn.api.my_registry_api.repositories;

import mkn.api.my_registry_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);
    User findUserByEmail(String email);
    User deleteUserByEmail(String email);
}
