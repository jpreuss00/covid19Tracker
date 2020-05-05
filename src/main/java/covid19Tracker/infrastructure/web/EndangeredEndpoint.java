package covid19Tracker.infrastructure.web;

import covid19Tracker.application.SightingService;
import covid19Tracker.domain.Sighting;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

public class EndangeredEndpoint extends AbstractHandler {

    private final static Logger logr = Logger.getLogger(EndangeredEndpoint.class.getName());

    private final CorsHandler corsHandler;
    private final SightingService sightingService;

    public EndangeredEndpoint(SightingService sightingService, CorsHandler corsHandler) {
        this.sightingService = sightingService;
        this.corsHandler = corsHandler;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        corsHandler.handleCors(request, response);
        baseRequest.setHandled(true);

        double latitude = 0;
        double longitude = 0;

        if (request.getParameter("latitude") != null) {
            latitude = Double.parseDouble(request.getParameter("latitude"));
        }
        if (request.getParameter("longitude") != null) {
            longitude = Double.parseDouble(request.getParameter("longitude"));
        }
        Date date = new Date();

        if (latitude != 0 && longitude != 0) {
            Sighting sighting = new Sighting(latitude, longitude, date);
            if (sightingService.isEndangered(sighting)) {
                response.setStatus(200);
            } else {
                response.setStatus(204);
            }
        } else {
            logr.info("There was an error with transferring the geolocation to the backend.");
            response.setStatus(400);
        }
        logr.fine("Endangered Page is running!");
    }

}