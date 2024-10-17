package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    Map<String, UserData> userDataMap = new HashMap<>();

    // User Data Methods:
    void createUser(String username, String password, String email) {
        userDataMap.put(username, new UserData(username, password, email));
    }

    public UserData getUser(String username) throws DataAccessException {
        if (userDataMap.containsKey(username)){
            return userDataMap.get(username);
        }
        else{
            throw new DataAccessException("Username doesn't exist");
        }
    }

}
