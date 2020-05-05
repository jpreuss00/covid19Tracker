package covid19Tracker;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.*;

public class LoggerConfiguration {

    public static void setUpLogger() throws IOException {
        LogManager.getLogManager().reset();

        FileHandler fh = new FileHandler("logs.log", true);
        fh.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);

        Logger global = Logger.getGlobal();
        global.setLevel(Level.WARNING);
        global.addHandler(ch);

        Logger covidTracker = Logger.getLogger("covid19Tracker");
        covidTracker.setLevel(Level.FINE);
        covidTracker.addHandler(fh);
        covidTracker.addHandler(ch);

    }
}
