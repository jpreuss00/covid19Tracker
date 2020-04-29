package covid19Tracker.infrastructure.database;

import covid19Tracker.domain.Sighting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class InsertInDatabase {

    private final Connection connection;

    public InsertInDatabase(Connection connection){
        this.connection = connection;
    }

    public boolean insertNewUserInDB(int userID, String deleteCode){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userData(userID, deleteCode)" + "VALUES (?, ?)");
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, deleteCode);
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public boolean checkForDoubles(int userID){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM userData WHERE userID = ?");
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getInt(1) == 1){
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

}