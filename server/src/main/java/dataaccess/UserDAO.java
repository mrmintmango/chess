package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class UserDAO implements UserDAOI{
    public Map<String, UserData> userDataMap = new HashMap<>();

    public void clear() {
        userDataMap.clear();
    }

    // User Data Methods:
    public void createUser(String username, String password, String email) {
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

    public boolean userFound(String username) {
        return userDataMap.containsKey(username);
    }

    public void registerUser(UserData user) {
        userDataMap.put(user.username(), user);
    }
}
