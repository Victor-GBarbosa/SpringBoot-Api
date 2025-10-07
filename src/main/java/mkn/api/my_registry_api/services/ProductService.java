package mkn.api.my_registry_api.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import mkn.api.my_registry_api.entities.Product;
import mkn.api.my_registry_api.repositories.CategoryRepository;
import mkn.api.my_registry_api.repositories.ProductRepository;
import mkn.api.my_registry_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpServletRequest requesst;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CategoryRepository categoryRepository;

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

    public Product insert(Product product) {
        String userEmail = tokenService.validadeToken(requesst.getHeader("Authorization"));

        product.setUser(userRepository.findUserByEmail(userEmail));
        product.setCategory(categoryRepository.findCategoryById(product.getCategory().getId()));
        System.out.println(product.getCategory().getName());

        return repository.save(product);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}