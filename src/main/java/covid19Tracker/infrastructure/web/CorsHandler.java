package covid19Tracker.infrastructure.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class CorsHandler {

    private final static Logger logr = Logger.getLogger(CorsHandler.class.getName());

    public void handleCors(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*"); // https://jpreuss00.github.io
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD");

        logr.fine("cors has been handled");
    }

}
