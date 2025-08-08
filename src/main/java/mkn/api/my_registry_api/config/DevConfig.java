package mkn.api.my_registry_api.config;

import mkn.api.my_registry_api.entities.Category;
import mkn.api.my_registry_api.entities.Product;
import mkn.api.my_registry_api.entities.User;
import mkn.api.my_registry_api.entities.enums.UserRole;
import mkn.api.my_registry_api.repositories.CategoryRepository;
import mkn.api.my_registry_api.repositories.ProductRepository;
import mkn.api.my_registry_api.repositories.UserRepository;
import mkn.api.my_registry_api.services.TokenService;
import org.antlr.v4.runtime.Token;
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
    @Autowired
    TokenService tokenService;


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

        
        System.out.println(tokenService.validadeToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6InZpY3RvcjVAZ21haWwuY29tIiwiZXhwIjoxNzU0Njg4NjE0fQ.yvrWQSIGS_7h92kEXaEkv6T-G2hFPHGT9-8AH0fPnhU"));
        System.out.printf("Let's go, fellas");
    }
}
