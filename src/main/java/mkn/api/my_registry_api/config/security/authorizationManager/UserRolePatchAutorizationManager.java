package mkn.api.my_registry_api.config.security.authorizationManager;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserRolePatchAutorizationManager implements org.springframework.security.authorization.AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;
    private final Pattern emailPattern = Pattern.compile("/users/([^/]+)");

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();

        String pathEmail = AuthorizationManagerUtils.getEmailOnURI(context);

            JsonNode jsonNode = AuthorizationManagerUtils.getRequestBody(context);
            Integer newRole = jsonNode.get("role").asInt();

            String token = request.getHeader("Authorization");


            User user = userRepository.findUserByEmail(tokenService.validadeToken(token));
            System.out.println("TOKEN = " + token + tokenService.validadeToken(token) + "\n" +  pathEmail +"\n\n\n\n\n\n");
            System.out.println(pathEmail == user.getEmail());
            if (user.getRole() <= 3 && user.getEmail().equals(pathEmail)) {
                if(newRole <= 2) {
                    return new AuthorizationDecision(true);
                } else {
                    return new AuthorizationDecision(false);
                }
            } else if (user.getRole() > 3) {
                return new AuthorizationDecision(true);
            } else {
                return new AuthorizationDecision(false);
            }
    }
}
