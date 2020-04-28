package covid19Tracker.domain;

public class User {

    public int userID;
    public String deleteCode;

    public User(int userID, String deleteCode){
        this.userID = userID;
        this.deleteCode = deleteCode;
    }

}