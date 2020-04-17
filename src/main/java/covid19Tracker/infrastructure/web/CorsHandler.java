package covid19Tracker.infrastructure.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsHandler {

    public void handleCors(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "https://jpreuss00.github.io");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD");

        if(request.getRemoteHost().equals("null")) { // allow local work, better remove
            response.setHeader("Access-Control-Allow-Origin", "null");
        }
    }

}
