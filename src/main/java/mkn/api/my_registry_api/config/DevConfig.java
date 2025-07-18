package mkn.api.my_registry_api.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import mkn.api.my_registry_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
//            User newser = new User("Carlos Jhonathan Andre Santos", "carlosjhon@sanandre.com", "newPassowrd", "91 8382-9239", "007-133-190-00" );
//            userRepository.save(newser);

        String pass = "newPassowrd";
        String hashPass = "-1821465749";
        System.out.println("senha: " + pass);
        System.out.println("HashCode da senha: " + pass.hashCode());
        System.out.println("Hash code do Hashcode da senha: " + hashPass.hashCode());
        System.out.println("Hashcode da senha = hash code do Hashcode da senha? " + (pass.hashCode() == hashPass.hashCode()));
    }
}
