package mkn.api.my_registry_api.config.security;

import jakarta.servlet.http.HttpServletRequest;
import mkn.api.my_registry_api.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;


import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserAuthorizationManager implements org.springframework.security.authorization.AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private TokenService tokenService;
    private final Pattern emailPattern = Pattern.compile("/users/email/([^/]+)");

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String path = request.getRequestURI();

        Matcher matcher = emailPattern.matcher(path);
        if (matcher.find()) {
            String emailNoPath = matcher.group(1);


            String token = request.getHeader("Authorization");
            if (token != null) {

                String emailNoToken = tokenService.validadeToken(token);

                return new AuthorizationDecision(emailNoPath.equals(emailNoToken));
            }
        }

        return new AuthorizationDecision(false);
    }
}
