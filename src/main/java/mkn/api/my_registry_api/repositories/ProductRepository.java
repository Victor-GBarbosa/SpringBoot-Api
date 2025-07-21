package mkn.api.my_registry_api.repositories;

import mkn.api.my_registry_api.entities.Product;
import mkn.api.my_registry_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByUserEmail(String Email);

    Product findAllByUserId(Long id);
}
