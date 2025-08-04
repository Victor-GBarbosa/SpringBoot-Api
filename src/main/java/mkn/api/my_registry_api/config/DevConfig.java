package mkn.api.my_registry_api.config;

import mkn.api.my_registry_api.entities.Category;
import mkn.api.my_registry_api.entities.Product;
import mkn.api.my_registry_api.entities.User;
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

//            User newser = new User("Victor", "victor@gmail.com", passwordEncoder.encode("newPassowrd"), "91 8312-9239", "007-132-190-00" );
//            newser.setRoleStatus(UserRole.SELLER);
//            userRepository.save(newser);
//
//        Category category = new Category("Categoria massa");
//        categoryRepository.save(category);
//        Product product = new Product(newser,9.99, "Coisa", "", "Alguma coisa legal", category);
//        productRepository.save(product);
//
//        Product produc2 = new Product(newser,9.99, "Outra Coisa", "", "Alguma coisa legal", category);
//        productRepository.save(produc2);
        System.out.printf("Let's go, fellas");
    }
}
