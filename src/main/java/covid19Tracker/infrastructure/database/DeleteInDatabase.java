package covid19Tracker.infrastructure.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeleteInDatabase {

    private final Connection connection;

    public DeleteInDatabase(Connection connection){
        this.connection = connection;
    }

    public void deleteUser(String deleteCode){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM userdata WHERE deletecode = ?");
            preparedStatement.setString(1, deleteCode);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public boolean validateCode(String deleteCode){

        if(deleteCode.length() == 10){
            String first = deleteCode.substring(0, 4);
            String second = deleteCode.substring(4, 5);
            String third = deleteCode.substring(5, 10);
            System.out.printf("first: %s second: %s third: %s",first,second,third);
            if(first.matches("[0-9]") && second.equals("#") && third.matches("[a-z]")){
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT (*) FROM userdata WHERE deletecode = ?");
                    preparedStatement.setString(1, deleteCode);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        return (resultSet.getInt(1) > 0);
                    } else {
                        return false;
                    }
                } catch (Exception e){
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            }
        } else {
            return false;
        }
        return false;
    }
}
