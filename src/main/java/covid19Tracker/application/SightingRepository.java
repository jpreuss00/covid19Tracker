package covid19Tracker.application;

import covid19Tracker.domain.Sighting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SightingRepository {

    private Connection connection;

    public SightingRepository(Connection connection) {
        this.connection = connection;
    }

    public void insertNewSightingInDB(int userID, Sighting sighting) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userLocation(userID, latitude, longitude, instant)" + "VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, userID);
            preparedStatement.setDouble(2, sighting.getLatitude());
            preparedStatement.setDouble(3, sighting.getLongitude());
            preparedStatement.setDate(4, new java.sql.Date(sighting.getInstant().getTime()));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public List<Sighting> getSightingsOutOfDB(int userID) {
        try {
            List<Sighting> sightings = new ArrayList<>();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM userLocation WHERE userID = ?");
            preparedStatement.setInt(1, userID);
            ResultSet result = preparedStatement.executeQuery();
            int limit = 0;
            if (result.next()) {
                limit = result.getInt(1);
            }
            if (limit > 0) {
                preparedStatement = connection.prepareStatement("SELECT * FROM userLocation WHERE userID = ?");
                preparedStatement.setInt(1, userID);
                result = preparedStatement.executeQuery();

                for (int i = 0; i < limit; i++) {
                    if (result.next()) {

                        double latitude = 0;
                        double longitude = 0;
                        Date date = new Date();

                        for (int j = 2; j < 4; j++) {

                            switch (j) {
                                case 2:
                                    latitude = result.getDouble(j);
                                    break;
                                case 3:
                                    longitude = result.getDouble(j);
                                    break;
                                case 4:
                                    date = result.getDate(j);
                                    ;
                                    break;
                            }
                        }
                        Sighting sighting = new Sighting(latitude, longitude, date);
                        sightings.add(sighting);
                    } else {
                        break;
                    }
                }
                return sightings;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }
}
