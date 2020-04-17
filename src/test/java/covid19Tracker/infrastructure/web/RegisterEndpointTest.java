package covid19Tracker.infrastructure.web;

import covid19Tracker.application.AccountService;
import covid19Tracker.domain.User;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Request;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterEndpointTest {

    private RegisterEndpoint registerEndpoint;
    private AccountService accountService;
    private User user;
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
        this.user = Mockito.mock(User.class);
        this.accountService = Mockito.mock(AccountService.class);
        this.corsHandler = Mockito.mock(CorsHandler.class);
        this.registerEndpoint = new RegisterEndpoint(accountService, corsHandler);
    }

    @Test
    public void handle_should_returnUser_onCorrect_getRequest() throws IOException {

        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.doReturn(writer).when(response).getWriter();

        Mockito.doReturn("https://jpreuss00.github.io").when(request).getRemoteHost();
        user = new User( 1234,"1234#abcde");
        Mockito.doReturn(user).when(accountService).register();

        registerEndpoint.handle(target, baseRequest, request, response);

        Mockito.verify(response).setContentType(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());
        Mockito.verify(writer).print(Mockito.any(JSONObject.class));
    }

    @Test
    public void handle_should_Set_StatusTo500_if_User_notGenerated() throws  IOException{

        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.doReturn(writer).when(response).getWriter();

        Mockito.doReturn("https://jpreuss00.github.io").when(request).getRemoteHost();
        //Mockito.doReturn(null).when(accountService).register(); not needed, mockito always returns null

        registerEndpoint.handle(target, baseRequest, request, response);

        Mockito.verify(response).setStatus(500);
        Mockito.verify(writer, Mockito.never()).print(Mockito.any(JSONObject.class));
    }

}
