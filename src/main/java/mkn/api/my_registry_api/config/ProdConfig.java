package mkn.api.my_registry_api.config;

import mkn.api.my_registry_api.entities.Category;
import mkn.api.my_registry_api.entities.Product;
import mkn.api.my_registry_api.entities.enums.UserRole;
import mkn.api.my_registry_api.repositories.CategoryRepository;
import mkn.api.my_registry_api.repositories.ProductRepository;
import mkn.api.my_registry_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("prod")
public class ProdConfig implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;


    @Override
    public void run(String... args) throws Exception {


        Category category = new Category("category");
        categoryRepository.save(category);
        System.out.printf("Let's go, fellas");
    }
}
