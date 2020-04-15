package covid19Tracker.infrastructure;

import java.lang.reflect.Array;
import java.util.Random;

public class UserGenerator {

    public int getRandomUserID(){
        Random random = new Random();
        int userID = 0;
        while(userID < 999){
            userID = random.nextInt(10000);
        }
        return userID;
    }

    public String getRandomDeleteCode(){
        Random random = new Random();
        String deleteCode = "";
        for(int i = 0; i < 5; i++){
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            deleteCode += alphabet[random.nextInt(26)];
        }
        return deleteCode;
    }
}
