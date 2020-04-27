package covid19Tracker.infrastructure.web;

import covid19Tracker.application.AccountService;
import covid19Tracker.domain.User;
import covid19Tracker.infrastructure.database.DeleteInDatabase;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Request;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteEndpointTest {

    private DeleteInDatabase deleteInDatabase;
    private DeleteEndpoint deleteEndpoint;
    private String target;
    private Request baseRequest;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private CorsHandler corsHandler;

    @Before
    public void setUp(){
        this.baseRequest = Mockito.mock(Request.class);
        this.request = Mockito.mock(HttpServletRequest.class);
        this.response = Mockito.mock(HttpServletResponse.class);
        this.corsHandler = Mockito.mock(CorsHandler.class);
        this.deleteInDatabase = Mockito.mock(DeleteInDatabase.class);
        this.deleteEndpoint = new DeleteEndpoint(corsHandler, deleteInDatabase);
    }

    @Test
    public void handle_should_deleteUser_for_ExistingCode() throws IOException {

        String deleteCode = "1234#abcde";

        Mockito.doReturn(deleteCode).when(request).getParameter("deleteCode");
        Mockito.doReturn(true).when(deleteInDatabase).validateCode(deleteCode);

        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.doReturn(writer).when(response).getWriter();

        deleteEndpoint.handle(target, baseRequest, request, response);

        Mockito.verify(response).setStatus(204);
    }

    @Test
    public void handle_should_return_error_for_unknownCode() throws  IOException{

        String deleteCode = "1234#abcde";

        Mockito.doReturn(deleteCode).when(request).getParameter("deleteCode");
        Mockito.doReturn(false).when(deleteInDatabase).validateCode(deleteCode);

        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.doReturn(writer).when(response).getWriter();

        deleteEndpoint.handle(target, baseRequest, request, response);

        Mockito.verify(response).setStatus(404);
    }

    @Test
    public void handle_should_return_error_for_emptyCode() throws  IOException{

        String deleteCode = null;

        Mockito.doReturn(deleteCode).when(request).getParameter("deleteCode");

        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.doReturn(writer).when(response).getWriter();

        deleteEndpoint.handle(target, baseRequest, request, response);

        Mockito.verify(response).setStatus(400);
    }

}
