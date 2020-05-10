package covid19Tracker.infrastructure.web;

import covid19Tracker.application.AccountService;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class DeleteEndpoint extends AbstractHandler {

    private final static Logger logr = Logger.getLogger(DeleteEndpoint.class.getName());

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
                logr.info("deleted user with valid deleteCode");
                response.setStatus(204);
            } else {
                logr.info("wrong deleteCode");
                response.setStatus(404);
            }
        } else {
            logr.info("missing deleteCode");
            response.setStatus(400);
        }
        logr.fine("Delete Page is running...");
    }
}
