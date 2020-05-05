package covid19Tracker.infrastructure.web;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HealthEndpoint extends AbstractHandler {

    private final static java.util.logging.Logger logr = java.util.logging.Logger.getLogger("Logger");


    private final CorsHandler corsHandler;

    public HealthEndpoint(CorsHandler corsHandler) {
        this.corsHandler = corsHandler;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        corsHandler.handleCors(request, response);

        JSONObject json = new JSONObject().put("Status", "up");
        response.getWriter().print(json);
        baseRequest.setHandled(true);
        logr.fine("Health Page is running!");
    }
}