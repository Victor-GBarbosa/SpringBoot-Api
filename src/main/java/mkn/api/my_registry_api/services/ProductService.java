package mkn.api.my_registry_api.services;

import jakarta.transaction.Transactional;
import mkn.api.my_registry_api.entities.Product;
import mkn.api.my_registry_api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public List<Product> findAllByUserEmail (String email) {
        return repository.findAllByUserEmail(email);
    }

    public Product findByUserId(long id) {
        return repository.findAllByUserId(id);
    }

    public Product findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id)); //!-1
    }

    public Product insert(Product user) {
        return repository.save(user);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }


}
