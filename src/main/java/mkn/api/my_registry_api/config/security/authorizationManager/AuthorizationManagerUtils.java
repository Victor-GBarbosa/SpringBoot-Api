package mkn.api.my_registry_api.config.security.authorizationManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationManagerUtils {

    static public JsonNode getRequestBody(RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        try {
            BufferedReader reader = request.getReader();
            StringBuilder bodyBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                bodyBuilder.append(line);
            }
            String requestBody = bodyBuilder.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            return jsonNode;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static public String getEmailOnURI(RequestAuthorizationContext context) {
        Pattern emailPattern = Pattern.compile("/users/([^/]+)");
        HttpServletRequest request = context.getRequest();
        String path = request.getRequestURI();
        Matcher matcher = emailPattern.matcher(path);
        String pathEmail = null;

        if (matcher.find()) {
            pathEmail = matcher.group(1);
        }
        return pathEmail;
    }
}
