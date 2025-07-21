package mkn.api.my_registry_api.repositories;

import mkn.api.my_registry_api.entities.Category;
import mkn.api.my_registry_api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
