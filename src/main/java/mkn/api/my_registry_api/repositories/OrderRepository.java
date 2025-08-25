package mkn.api.my_registry_api.repositories;

import mkn.api.my_registry_api.entities.Order;
import mkn.api.my_registry_api.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
