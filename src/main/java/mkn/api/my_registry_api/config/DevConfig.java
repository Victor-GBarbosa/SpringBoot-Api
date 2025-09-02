package mkn.api.my_registry_api.config;

import mkn.api.my_registry_api.entities.*;
import mkn.api.my_registry_api.entities.enums.UserRole;
import mkn.api.my_registry_api.repositories.*;
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
    OrderProductRepository orderProductRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TokenService tokenService;


    @Override
    public void run(String... args) throws Exception {
        User newser = new User("Victor", "victor5@gmail.com", passwordEncoder.encode("123"), "91 8312-9239", "007-132-190-00" );
        newser.setRoleStatus(UserRole.MASTER);
        userRepository.save(newser);

        Category category = new Category("Categoria massa");
        categoryRepository.save(category);
        Product product = new Product(newser,9.99, "Coisa", "", "Alguma coisa legal", category);
        productRepository.save(product);

        Product product1 = new Product(newser,9.99, "Outra Coisa", "", "Alguma coisa legal", category);
        productRepository.save(product1);

        Order order = new Order();
        OrderProduct op = new OrderProduct(product, 1, order);
        order.addOrderProduct(op);
        orderRepository.save(order);
        orderProductRepository.save(op);

        OrderProduct op2 = new OrderProduct(product1, 2, order);
        order.addOrderProduct(op2);
        orderProductRepository.save(op2);
        orderRepository.save(order);

//        Order order2 = new Order();
//        orderRepository.save(order2);
//        OrderProduct op12 = new OrderProduct(product, 1, order2);
//        orderProductRepository.save(op12);
//        OrderProduct op22 = new OrderProduct(product1, 2, order2);
//        orderProductRepository.save(op22);

//        order.addOrderProduct(op);
//        order.addOrderProduct(op2);
        newser.addOrder(order);
        orderRepository.save(order);
        userRepository.save(newser);



//        User findedUser = userRepository.findUserByEmail(newser.getEmail());
//        System.out.println(findedUser.getOrder().getOrderProductList().get(0).getProduct().getName());

        System.out.printf("Let's go, fellas");
    }
}
