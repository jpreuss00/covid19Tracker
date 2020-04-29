package covid19Tracker.infrastructure.web;

import covid19Tracker.application.SightingRepository;
import covid19Tracker.domain.Sighting;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class SightingEndpoint extends AbstractHandler {

    private SightingRepository sightingRepository;
    private final CorsHandler corsHandler;

    public SightingEndpoint(SightingRepository sightingRepository, CorsHandler corsHandler) {
        this.sightingRepository = sightingRepository;
        this.corsHandler = corsHandler;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        corsHandler.handleCors(request, response);
        baseRequest.setHandled(true);
        int userID = 0;
        double latitude = 0;
        double longitude = 0;

        if (request.getParameter("userID") != null) {
            userID = Integer.parseInt(request.getParameter("userID"));
        }
        if (request.getParameter("latitude") != null) {
            latitude = Double.parseDouble(request.getParameter("latitude"));
        }
        if (request.getParameter("longitude") != null) {
            longitude = Double.parseDouble(request.getParameter("latitude"));
        }
        Date date = new Date();
        if (userID != 0 && latitude != 0 && longitude != 0) {
            Sighting sighting = new Sighting(latitude, longitude, date);
            sightingRepository.insertNewSightingInDB(userID, sighting);
            response.setStatus(204);
        } else {
            response.setStatus(400);
            return;
        }
        System.out.println("Sighting Page is running...");
    }
}
