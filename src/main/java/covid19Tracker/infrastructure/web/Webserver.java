package covid19Tracker.infrastructure.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class Webserver {
    public void startJetty() throws Exception {

        final ContextHandler health = new ContextHandler("/health");

        health.setAllowNullPathInfo(true);

        ContextHandlerCollection contexts = new ContextHandlerCollection(health);

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
