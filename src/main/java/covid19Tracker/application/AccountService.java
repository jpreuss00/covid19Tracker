package covid19Tracker.application;

import covid19Tracker.domain.User;
import covid19Tracker.infrastructure.UserGenerator;
import covid19Tracker.infrastructure.database.DeleteInDatabase;
import covid19Tracker.infrastructure.database.InsertInDatabase;

public class AccountService {

    private final UserGenerator userGenerator;
    private final InsertInDatabase insertInDatabase;
    private final DeleteInDatabase deleteInDatabase;

    public AccountService(UserGenerator userGenerator, InsertInDatabase insertInDatabase, DeleteInDatabase deleteInDatabase) {
        this.userGenerator = userGenerator;
        this.insertInDatabase = insertInDatabase;
        this.deleteInDatabase = deleteInDatabase;
    }

    public User register() {

        int userID = userGenerator.getRandomUserID();
        while (insertInDatabase.checkForDoubles(userID)) {
            userID = userGenerator.getRandomUserID();
        }

        String deleteCode = userID + "#" + userGenerator.getRandomDeleteCode();

        if (!insertInDatabase.insertNewUserInDB(userID, deleteCode)) {
            return null;
        }
        return new User(userID, deleteCode);
    }

    public boolean delete(String deleteCode) {
        if (deleteInDatabase.validateCode(deleteCode)) {
            deleteInDatabase.deleteUser(deleteCode);
            return true;
        } else {
            return false;
        }
    }
}
