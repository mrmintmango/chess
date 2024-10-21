package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAOI{
    public Map<String, UserData> userDataMap = new HashMap<>();

    public void clear() {
        userDataMap.clear();
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

    public void putUser(String name, UserData user) {
        userDataMap.put(name, user);
    }

    public int getUserSize() {
        return userDataMap.size();
    }

}
