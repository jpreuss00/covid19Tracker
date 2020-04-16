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


    private final AccountService accountService;

    public RegisterEndpoint(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");

        String registration = "";

        if (request.getParameter("registration") != null) {
            registration = request.getParameter("registration");
        }

        if (registration.equals("true")) {
            User user = accountService.register();
            if (user == null) {
                response.setStatus(500);
            }
        JSONObject data = new JSONObject().put("id", user.userID);
        data.put("deletecode", user.deleteCode);
        response.setContentType(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());
        response.getWriter().print(data);
        }

        baseRequest.setHandled(true);
        System.out.println("Register Page is running...");
    }
}
