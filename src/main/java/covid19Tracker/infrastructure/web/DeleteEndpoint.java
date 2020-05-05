package covid19Tracker.infrastructure.web;

import covid19Tracker.application.AccountService;
import covid19Tracker.infrastructure.database.DeleteInDatabase;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteEndpoint extends AbstractHandler {

    private final static java.util.logging.Logger logr = java.util.logging.Logger.getLogger("Logger");

    private final AccountService accountService;
    private final CorsHandler corsHandler;

    public DeleteEndpoint(AccountService accountService, CorsHandler corsHandler) {
        this.accountService = accountService;
        this.corsHandler = corsHandler;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        corsHandler.handleCors(request, response);
        baseRequest.setHandled(true);

        if (request.getParameter("deleteCode") != null && !request.getParameter("deleteCode").isEmpty()) {
            String deleteCode = request.getParameter("deleteCode");
            if (accountService.delete(deleteCode)) {
                logr.finer("deleted user with valid deleteCode");
                response.setStatus(204);
            } else {
                logr.finer("wrong deleteCode");
                response.setStatus(404);
            }
        } else {
            logr.finer("missing deleteCode");
            response.setStatus(400);
        }
        logr.fine("Delete Page is running...");
    }
}
