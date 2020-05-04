package covid19Tracker;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.*;

public class Logger {

    private final static java.util.logging.Logger logr = java.util.logging.Logger.getLogger("Logger");

    public static void setUpLogger() throws IOException {
        LogManager.getLogManager().reset();
        logr.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        logr.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("logs.log", true);
            logr.addHandler(fh);
        } catch (Exception e) {
            logr.log(Level.SEVERE, "File logger not working.", e);
        }

    }
}
