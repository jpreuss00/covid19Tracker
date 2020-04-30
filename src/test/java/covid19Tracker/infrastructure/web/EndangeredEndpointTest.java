package covid19Tracker.infrastructure.web;

import covid19Tracker.application.SightingService;
import covid19Tracker.domain.Sighting;
import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EndangeredEndpointTest {

    private SightingService sightingService;
    private EndangeredEndpoint endangeredEndpoint;
    private String target;
    private Sighting sighting;
    private Request baseRequest;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private CorsHandler corsHandler;

    @Before
    public void setUp(){
        this.sighting = Mockito.mock(Sighting.class);
        this.baseRequest = Mockito.mock(Request.class);
        this.request = Mockito.mock(HttpServletRequest.class);
        this.response = Mockito.mock(HttpServletResponse.class);
        this.corsHandler = Mockito.mock(CorsHandler.class);
        this.sightingService = Mockito.mock(SightingService.class);
        this.endangeredEndpoint = new EndangeredEndpoint(sightingService, corsHandler);
    }

    @Test
    public void handle_should_setStatus_to204_onNoCovid_inNear() throws IOException {
        String latitude = "52.1313922";
        String longitude = "13.216249399999999";

        Mockito.doReturn(latitude).when(request).getParameter("latitude");
        Mockito.doReturn(longitude).when(request).getParameter("longitude");

        Mockito.doReturn(false).when(sightingService).isEndangered(Mockito.any());
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);

        endangeredEndpoint.handle(target, baseRequest, request, response);

        System.out.println("Test 1: ");
        Mockito.verify(response).setStatus(argument.capture());
        assertEquals(Integer.valueOf(204), argument.getValue());
    }

    @Test
    public void handle_should_setStatus_to200_onCovid_inNear() throws IOException {
        String latitude = "52.1313922";
        String longitude = "13.216249399999999";

        Mockito.doReturn(latitude).when(request).getParameter("latitude");
        Mockito.doReturn(longitude).when(request).getParameter("longitude");

        Mockito.doReturn(true).when(sightingService).isEndangered(Mockito.any());
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);

        endangeredEndpoint.handle(target, baseRequest, request, response);

        System.out.println("Test 2: ");
        Mockito.verify(response).setStatus(argument.capture());
        assertEquals(Integer.valueOf(200), argument.getValue());
    }

    @Test
    public void handle_should_setStatus_to400_onNo_Arguments() throws IOException {
        String latitude = "0";
        String longitude = "0";

        Mockito.doReturn(latitude).when(request).getParameter("latitude");
        Mockito.doReturn(longitude).when(request).getParameter("longitude");

        endangeredEndpoint.handle(target, baseRequest, request, response);

        System.out.println("Test 3: ");
        Mockito.verify(response).setStatus(400);
    }
}
