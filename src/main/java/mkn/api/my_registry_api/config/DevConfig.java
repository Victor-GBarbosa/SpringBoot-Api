package mkn.api.my_registry_api.config;

import mkn.api.my_registry_api.entities.User;
import mkn.api.my_registry_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {


        User user1 = new User("Jhon Marston", "marston.jhon@gmail.com", "123456", "(11) 98498-2921","111.222.333-09");
        User user2 = new User("Arthur Morgan", "arthur@hotmail.com", "382910", "(62) 9958-28169","011.261.123-07");
        userRepository.saveAll(Arrays.asList(user1, user2));
    }
}
