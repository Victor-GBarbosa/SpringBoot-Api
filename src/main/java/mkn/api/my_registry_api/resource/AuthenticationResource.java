package mkn.api.my_registry_api.resource;

import jakarta.validation.Valid;
import mkn.api.my_registry_api.entities.AuthenticationDTO;
import mkn.api.my_registry_api.entities.User;
import mkn.api.my_registry_api.entities.enums.UserRole;
import mkn.api.my_registry_api.repositories.UserRepository;
import mkn.api.my_registry_api.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"}, allowCredentials = "true")
public class AuthenticationResource {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User)auth.getPrincipal());

            return ResponseEntity.ok( "{\"token\": \"" + token + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("{\"Status\" : \"Credenciais invalidas\"}");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user) {

        if(this.repository.findUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }

        user.setRoleStatus(UserRole.USER);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User savedUser = this.repository.save(user);

        return ResponseEntity.ok(savedUser);
    }


}
