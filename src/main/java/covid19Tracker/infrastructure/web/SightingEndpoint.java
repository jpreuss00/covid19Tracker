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

    private final static java.util.logging.Logger logr = java.util.logging.Logger.getLogger("Logger");

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
            longitude = Double.parseDouble(request.getParameter("longitude"));
        }
        Date date = new Date();
        if (userID != 0 && latitude != 0 && longitude != 0) {
            Sighting sighting = new Sighting(latitude, longitude, date);
            sightingRepository.saveNewSighting(userID, sighting);
            logr.finer("New Sighting has been saved!");
            response.setStatus(204);
        } else {
            logr.finer("There was an error creating an new Sighting with given data: userID: " + userID + ", latitude: " + latitude + ", longitude: " + longitude + ", Date: " + date + ".");
            response.setStatus(400);
            return;
        }
        logr.fine("Sighting Page is running!");
    }
}
