package covid19Tracker.infrastructure.web;

import covid19Tracker.application.AccountService;
import covid19Tracker.domain.User;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterEndpoint extends AbstractHandler {

    private final static java.util.logging.Logger logr = java.util.logging.Logger.getLogger("Logger");

    private final AccountService accountService;
    private final CorsHandler corsHandler;

    public RegisterEndpoint(AccountService accountService, CorsHandler corsHandler) {
        this.accountService = accountService;
        this.corsHandler = corsHandler;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        corsHandler.handleCors(request, response);
        baseRequest.setHandled(true);

        User user = accountService.register();
        if (user == null) {
            response.setStatus(500);
            logr.info("There was an error creating a new user.");
            return;
        }

        JSONObject data = new JSONObject().put("id", user.userID);
        data.put("deletecode", user.deleteCode);
        response.setContentType(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());
        response.getWriter().print(data);
        logr.info("A new user has been created.");
        logr.info("Register Page is running!");
    }
}