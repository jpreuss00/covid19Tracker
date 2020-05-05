package covid19Tracker.infrastructure.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeleteInDatabase {

    private final static java.util.logging.Logger logr = java.util.logging.Logger.getLogger("Logger");

    private final Connection connection;

    public DeleteInDatabase(Connection connection) {
        this.connection = connection;
    }

    public void deleteUser(String deleteCode) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM userdata WHERE deletecode = ?");
            preparedStatement.setString(1, deleteCode);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            logr.warning(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public boolean validateCode(String deleteCode) {

        if (deleteCode.length() == 10) {
            if (deleteCode.matches("\\d{4}#[a-z]{5}")) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT (*) FROM userdata WHERE deletecode = ?");
                    preparedStatement.setString(1, deleteCode);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        return (resultSet.getInt(1) > 0);
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    logr.warning(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }
            }
        } else {
            return false;
        }
        return false;
    }
}
