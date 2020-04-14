package covid19Tracker.infrastructure.database;

import java.sql.*;

public class InitDatabase {

    private String host;
    private String user;
    private String password;
    private String database;
    private Connection connection;

    public InitDatabase(String host, String user, String password, String database) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    public boolean initiateDatabase(){
        try {
            String url = "jdbc:postgresql://" + host + ":5432/" + database;
            connection = DriverManager.getConnection(url, user, password);
            ResultSet resultDB = connection.getMetaData().getCatalogs();
            while (resultDB.next()) {
                String catalogs = resultDB.getString(1);
                if (database.equals(catalogs)) {
                    System.out.println("Database already exists...creating table");
                } else {
                    System.out.println("Creating Database...creating table next");
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("CREATE DATABASE " + database + "");
                }
            }
            connection.close();
            connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":5432/" + database + "?user=" + user + "&password=" + password);
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery("select COUNT(*) from pg_catalog.pg_tables where tablename = 'userdata'");

            if(result.next()){
               int resultString = result.getInt(1);
                if(resultString == 0){
                    System.out.println("Creating Table userData...creating table userLocation next");
                    statement.executeUpdate("CREATE TABLE userData (userID int, deleteCode varchar)");
                } else {
                    System.out.println("Table userData already exists...creating table userLocation");
                }
            }

            result = statement.executeQuery("select COUNT(*) from pg_catalog.pg_tables where tablename = 'userlocation'");

            if(result.next()){
                int resultString = result.getInt(1);
                if(resultString == 0){
                    System.out.println("Creating Table userLocation...starting app next");
                    statement.executeUpdate("CREATE TABLE userLocation (userID int, geolocation float)");
                } else {
                    System.out.println("Table userLocation already exists...starting app");
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }
}