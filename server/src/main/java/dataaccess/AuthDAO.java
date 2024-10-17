package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class AuthDAO {
    Map<String, AuthData> authDataMap = new HashMap<>();

    // Auth Data Methods:
    public void createAuth(String authToken, String username) {
        authDataMap.put(username, new AuthData(authToken, username));
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        if (authDataMap.containsKey(authToken)){
            return authDataMap.get(authToken);
        }
        else{
            throw new DataAccessException("AuthToken doesn't exist");
        }
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        if (authDataMap.containsKey(authToken)){
            authDataMap.remove(authToken);
        }
        else{
            throw new DataAccessException("AuthToken doesn't exist");
        }
    }

}
