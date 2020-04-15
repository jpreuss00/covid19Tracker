package covid19Tracker.application;

import covid19Tracker.domain.User;
import covid19Tracker.infrastructure.UserGenerator;
import covid19Tracker.infrastructure.database.InsertInDatabase;

public class AccountService {

    private final UserGenerator userGenerator;
    private final InsertInDatabase insertInDatabase;

    public AccountService(UserGenerator userGenerator, InsertInDatabase insertInDatabase){
        this.userGenerator = userGenerator;
        this.insertInDatabase = insertInDatabase;
    }

    public User register(){

            int userID = userGenerator.getRandomUserID();
            while(insertInDatabase.checkForDoubles(userID)){
                userID = userGenerator.getRandomUserID();
            }

           String deleteCode = userID+"#"+userGenerator.getRandomDeleteCode();


        if(!insertInDatabase.insertInDB(userID, deleteCode)){
            return null;
        }
        return new User(userID, deleteCode);
    }

    public void delete(String deleteCode){

    }
}
