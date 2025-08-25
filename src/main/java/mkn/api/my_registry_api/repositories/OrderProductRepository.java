package mkn.api.my_registry_api.repositories;

import mkn.api.my_registry_api.entities.OrderProduct;
import mkn.api.my_registry_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
