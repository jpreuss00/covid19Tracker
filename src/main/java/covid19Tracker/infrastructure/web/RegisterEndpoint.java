package covid19Tracker.infrastructure.web;

import covid19Tracker.infrastructure.UserGenerator;
import covid19Tracker.infrastructure.database.InsertInDatabase;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterEndpoint extends AbstractHandler {

    private final UserGenerator userGenerator;
    private final InsertInDatabase insertInDatabase;

    public RegisterEndpoint(UserGenerator userGenerator, InsertInDatabase insertInDatabase){
        this.userGenerator = userGenerator;
        this.insertInDatabase = insertInDatabase;
    }
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");

        String registration = "";
        int userID = 0;
        String deleteCode = "";

        if (request.getParameter("registration") != null) {
            registration = request.getParameter("registration");
        }

        if(registration.equals("true")){
           userID = userGenerator.getRandomUserID();
           while(insertInDatabase.checkForDoubles(userID)){
               userID = userGenerator.getRandomUserID();
           }
           deleteCode = userID+"#"+userGenerator.getRandomDeleteCode();
        }

        if(!insertInDatabase.insertInDB(userID, deleteCode)){
            response.setStatus(500);
        }

        JSONObject data = new JSONObject();
        JSONObject user = new JSONObject().put("userID", userID);
        user.put("deleteCode", deleteCode);
        data.put("User", user);
        response.getWriter().print(data);

        baseRequest.setHandled(true);
        System.out.println("Register Page is running...");
    }
}
