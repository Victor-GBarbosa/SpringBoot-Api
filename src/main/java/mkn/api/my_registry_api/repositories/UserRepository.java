package mkn.api.my_registry_api.repositories;

import mkn.api.my_registry_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


public interface UserRepository extends JpaRepository<User, Long> {

}
