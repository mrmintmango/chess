package dataaccess;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DataAccess {
    Map<String, >
    //Map<String, AuthData> authDataMap = new HashMap<String, AuthData>();
    //Map<String, GameData> gameDataMap = new HashMap<String, GameData>();
    //Map<String, UserData> userDataMap = new HashMap<String, UserData>();

    public DataAccess() {

    }

    void clear() {
        userDataMap.clear();
        authDataMap.clear();
        gameDataMap.clear();
    }

    void createUser(String username, String password, String email) {
        userDataMap.put(username, new UserData());
    }

}
