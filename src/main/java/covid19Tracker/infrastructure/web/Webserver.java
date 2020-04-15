package covid19Tracker.infrastructure.web;

import covid19Tracker.infrastructure.UserGenerator;
import covid19Tracker.infrastructure.database.InitDatabase;
import covid19Tracker.infrastructure.database.InsertInDatabase;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class Webserver {

    private final UserGenerator userGenerator;
    private final InsertInDatabase InsertInDatabase;

    public Webserver(UserGenerator userGenerator, InsertInDatabase InsertInDatabase){
        this.userGenerator = userGenerator;
        this.InsertInDatabase = InsertInDatabase;
    }

    public void startJetty() throws Exception {

        final ContextHandler health = new ContextHandler("/health");
        final ContextHandler register = new ContextHandler("/register");

        health.setAllowNullPathInfo(true);
        register.setAllowNullPathInfo(true);

        health.setHandler(new HealthEndpoint());
        register.setHandler(new RegisterEndpoint(userGenerator, InsertInDatabase));

        ContextHandlerCollection contexts = new ContextHandlerCollection(health, register);

        String port = System.getenv("PORT");
        if (port == null) {
            port = "8080";
        }
        System.out.println("PORT: " + port);
        final Server server = new Server(Integer.parseInt(port));

        server.setHandler(contexts);
        server.start();
        server.join();
    }
}