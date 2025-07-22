package mkn.api.my_registry_api.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import mkn.api.my_registry_api.entities.Category;
import mkn.api.my_registry_api.entities.Product;
import mkn.api.my_registry_api.entities.User;
import mkn.api.my_registry_api.repositories.CategoryRepository;
import mkn.api.my_registry_api.repositories.ProductRepository;
import mkn.api.my_registry_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {

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

            User newser = new User("Carlos Jhonathan Andre Santos", "carlosjhon@sanandre.com", passwordEncoder.encode("newPassowrd"), "91 8382-9239", "007-133-190-00" );
            userRepository.save(newser);

        Category category = new Category("Categoria massa");
        categoryRepository.save(category);
        Product product = new Product(newser,9.99, "Coisa", "", "Alguma coisa legal", category);
        productRepository.save(product);
        System.out.printf("Let's go, fellas");
    }
}
