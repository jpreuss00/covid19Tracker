package covid19Tracker.infrastructure.web;

import covid19Tracker.infrastructure.database.DeleteInDatabase;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteEndpoint extends AbstractHandler {

    private final CorsHandler corsHandler;
    private final DeleteInDatabase deleteInDatabase;

    public DeleteEndpoint(CorsHandler corsHandler, DeleteInDatabase deleteInDatabase){
        this.corsHandler = corsHandler;
        this.deleteInDatabase = deleteInDatabase;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        corsHandler.handleCors(request, response);
        baseRequest.setHandled(true);

        JSONObject data = new JSONObject();

        if (request.getParameter("deleteCode") != null) {
            String deleteCode = request.getParameter("deleteCode");
            if(deleteInDatabase.validateCode(deleteCode)){
                deleteInDatabase.deleteUser(deleteCode);
                data.put("deleteStatus", "successful");
            } else {
                data.put("deleteStatus", "non-existent");
            }
        } else {
            data.put("deleteStatus", "empty");
        }

        response.setContentType(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());
        response.getWriter().print(data);

        System.out.println("Delete Page is running...");
    }
}
