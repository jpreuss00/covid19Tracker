package covid19Tracker.infrastructure.web;

import covid19Tracker.application.AccountService;
import covid19Tracker.application.SightingRepository;
import covid19Tracker.application.SightingService;
import covid19Tracker.infrastructure.database.DeleteInDatabase;
import org.checkerframework.checker.units.qual.C;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import java.sql.Connection;

public class Webserver {

    private final AccountService accountService;
    private final CorsHandler corsHandler;
    private final SightingRepository sightingRepository;
    private final SightingService sightingService;

    public Webserver(AccountService accountService, SightingRepository sightingRepository, SightingService sightingService, CorsHandler corsHandler) {
        this.accountService = accountService;
        this.sightingRepository = sightingRepository;
        this.sightingService = sightingService;
        this.corsHandler = corsHandler;
    }

    public void startJetty() throws Exception {

        final ContextHandler health = new ContextHandler("/health");
        final ContextHandler register = new ContextHandler("/register");
        final ContextHandler delete = new ContextHandler("/delete");
        final ContextHandler sighting = new ContextHandler("/sighting");
        final ContextHandler endangered = new ContextHandler("/endangered");

        health.setAllowNullPathInfo(true);
        register.setAllowNullPathInfo(true);
        delete.setAllowNullPathInfo(true);
        sighting.setAllowNullPathInfo(true);
        endangered.setAllowNullPathInfo(true);

        health.setHandler(new HealthEndpoint(corsHandler));
        register.setHandler(new RegisterEndpoint(accountService, corsHandler));
        delete.setHandler(new DeleteEndpoint(accountService, corsHandler));
        sighting.setHandler(new SightingEndpoint(sightingRepository, corsHandler));
        endangered.setHandler(new EndangeredEndpoint(sightingService, corsHandler));

        ContextHandlerCollection contexts = new ContextHandlerCollection(health, register, delete, sighting, endangered);

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