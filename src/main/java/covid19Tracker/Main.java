/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package covid19Tracker;

import covid19Tracker.application.AccountService;
import covid19Tracker.application.SightingRepository;
import covid19Tracker.application.SightingService;
import covid19Tracker.infrastructure.UserGenerator;
import covid19Tracker.infrastructure.database.ConnectToDatabase;
import covid19Tracker.infrastructure.database.DeleteInDatabase;
import covid19Tracker.infrastructure.database.InitDatabase;
import covid19Tracker.infrastructure.database.InsertInDatabase;
import covid19Tracker.infrastructure.web.CorsHandler;
import covid19Tracker.infrastructure.web.Webserver;

import java.sql.Connection;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws Exception {

        LoggerConfiguration.setUpLogger();
        final Logger logr = Logger.getLogger(Main.class.getName());

        String database_url = System.getenv("DATABASE_URL");
        String host = "";
        String user = "";
        String password = "";
        String database = "";

        if (database_url != null && !database_url.isEmpty()) {
            host = database_url.substring(91, 132);
            user = database_url.substring(11, 25);
            password = database_url.substring(26, 90);
            database = database_url.substring(138, 152);
        } else {
            host = System.getenv("DBHOST");
            user = System.getenv("DBUSER");
            password = System.getenv("DBPWD");
            database = System.getenv("DB");
        }

        if (host == null || host.isEmpty() || user == null || user.isEmpty() || password == null || password.isEmpty() || database == null || database.isEmpty()) {
            logr.config("Missing environment variables");
            System.exit(1);
        }
        logr.config("Starting app with host: " + host + ", user: " + user + ", database: " + database + ", password " + password + ".\n");

        InitDatabase initDatabase = new InitDatabase(host, user, password, database);
        initDatabase.initiateDatabase();

        ConnectToDatabase connectToDatabase = new ConnectToDatabase(host, user, password, database);
        Connection connection = connectToDatabase.connect();

        DeleteInDatabase deleteInDatabase = new DeleteInDatabase(connection);
        InsertInDatabase insertInDatabase = new InsertInDatabase(connection);

        UserGenerator userGenerator = new UserGenerator();
        AccountService accountService = new AccountService(userGenerator, insertInDatabase, deleteInDatabase);

        SightingRepository sightingRepository = new SightingRepository(connection);
        SightingService sightingService = new SightingService(sightingRepository);

        CorsHandler corsHandler = new CorsHandler();
        new Webserver(accountService, sightingRepository, sightingService, corsHandler).startJetty();
    }
}