package mkn.api.my_registry_api.config.security;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import mkn.api.my_registry_api.config.security.wrapper.CachedBodyHttpServletRequest;
import mkn.api.my_registry_api.entities.User;
import mkn.api.my_registry_api.repositories.UserRepository;
import mkn.api.my_registry_api.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

@Component
public class UserRolePatchAutorizationManager implements org.springframework.security.authorization.AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        try {
            BufferedReader reader = request.getReader();
            StringBuilder bodyBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                bodyBuilder.append(line);
            }
            String requestBody = bodyBuilder.toString();

            // Parse do JSON para extrair a nova role
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            Integer newRole = jsonNode.get("role").asInt();

            String token = request.getHeader("Authorization");


            User user = userRepository.findUserByEmail(tokenService.validadeToken(token));

            if (user.getRole() <= 3) {
                if(newRole <= 2) {
                    return new AuthorizationDecision(true);
                } else {
                    return new AuthorizationDecision(false);
                }
            } else {
                return new AuthorizationDecision(true);
            }
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
