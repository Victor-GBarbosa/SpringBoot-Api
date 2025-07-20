package mkn.api.my_registry_api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import mkn.api.my_registry_api.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    public Instant genExpirationDate() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate()).sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro while generating token: " + e);
        }
    }

    public String validadeToken (String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            String subject = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
            System.out.println("Subject extraído do token: " + subject);
            return subject;

        } catch (JWTVerificationException e) {
            return "";
        }
    }

}
