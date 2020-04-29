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

    public List<Sighting> getSightingsOutOfDBInNear(Sighting sighting) {
        try {
            List<Sighting> sightings = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT (*) FROM userLocation WHERE (latitude BETWEEN ? AND ?) AND (longitude BETWEEN ? AND ?)");
            preparedStatement.setDouble(1, (sighting.getLatitude() - 0.001));
            preparedStatement.setDouble(2, (sighting.getLatitude() + 0.001));
            preparedStatement.setDouble(3, (sighting.getLongitude() - 0.001));
            preparedStatement.setDouble(4, (sighting.getLongitude() + 0.001));
            ResultSet result = preparedStatement.executeQuery();
            int limit = 0;
            if (result.next()) {
                limit = result.getInt(1);
            }
            if (limit > 0) {
                preparedStatement = connection.prepareStatement("SELECT * FROM userLocation WHERE (latitude BETWEEN ? AND ?) AND (longitude BETWEEN ? AND ?)");
                preparedStatement.setDouble(1, (sighting.getLatitude() - 0.001));
                preparedStatement.setDouble(2, (sighting.getLatitude() + 0.001));
                preparedStatement.setDouble(3, (sighting.getLongitude() - 0.001));
                preparedStatement.setDouble(4, (sighting.getLongitude() + 0.001));
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
                                    break;
                            }
                        }
                        Sighting newSighting = new Sighting(latitude, longitude, date);
                        sightings.add(newSighting);
                    } else {
                        break;
                    }
                }
                return sightings;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }
}
