package covid19Tracker.infrastructure.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

public class InsertInDatabase {

    private final static Logger logr = Logger.getLogger(InsertInDatabase.class.getName());

    private final Connection connection;

    public InsertInDatabase(Connection connection) {
        this.connection = connection;
    }

    public boolean insertNewUserInDB(int userID, String deleteCode) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userData(userID, deleteCode)" + "VALUES (?, ?)");
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, deleteCode);
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            logr.warning(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public boolean checkForDoubles(int userID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM userData WHERE userID = ?");
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            logr.warning(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

}