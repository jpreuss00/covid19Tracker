package covid19Tracker.infrastructure.web;

import covid19Tracker.application.AccountService;
import covid19Tracker.infrastructure.database.DeleteInDatabase;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import java.sql.Connection;

public class Webserver {

    private final AccountService accountService;
    private final CorsHandler corsHandler;
    private final DeleteInDatabase deleteInDatabase;

    public Webserver(AccountService accountService, CorsHandler corsHandler, DeleteInDatabase deleteInDatabase){
        this.accountService = accountService;
        this.corsHandler = corsHandler;
        this.deleteInDatabase = deleteInDatabase;
    }

    public void startJetty() throws Exception {

        final ContextHandler health = new ContextHandler("/health");
        final ContextHandler register = new ContextHandler("/register");
        final ContextHandler delete = new ContextHandler("/delete");

        health.setAllowNullPathInfo(true);
        register.setAllowNullPathInfo(true);
        delete.setAllowNullPathInfo(true);

        health.setHandler(new HealthEndpoint());
        register.setHandler(new RegisterEndpoint(accountService, corsHandler));
        delete.setHandler(new DeleteEndpoint(corsHandler, deleteInDatabase));

        ContextHandlerCollection contexts = new ContextHandlerCollection(health, register, delete);

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