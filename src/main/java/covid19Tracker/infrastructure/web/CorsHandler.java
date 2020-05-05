package covid19Tracker.infrastructure.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsHandler {

    private final static java.util.logging.Logger logr = java.util.logging.Logger.getLogger("Logger");


    public void handleCors(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*"); // https://jpreuss00.github.io
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD");

        logr.fine("cors has been handled");
    }

}
