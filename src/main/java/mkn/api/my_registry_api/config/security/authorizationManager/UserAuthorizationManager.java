package mkn.api.my_registry_api.config.security.authorizationManager;

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
    private final Pattern emailPattern = Pattern.compile(".*/users/.*?([^@/\\s]+@[^@/\\s]+\\.[^/\\s]+)");

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String path = request.getRequestURI();
        System.out.println("URL: " + path);
        System.out.println("aaaaaa");
        Matcher matcher = emailPattern.matcher(path);
//        System.out.println(matcher.group(1));
        if (matcher.find()) {
            String emailNoPath = matcher.group(1);

            String token = request.getHeader("Authorization");
            System.out.println(token);
            System.out.println(tokenService.validadeToken(token));
            System.out.println(emailNoPath);
            if (token != null) {

                String emailNoToken = tokenService.validadeToken(token);
                return new AuthorizationDecision(emailNoPath.equals(emailNoToken));
            }
            System.out.println("entrou no if");
        }
        System.out.println("talvez não tenha entrado no if");
        return new AuthorizationDecision(false);
    }
}
