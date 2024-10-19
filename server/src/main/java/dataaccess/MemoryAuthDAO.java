package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAOI {
    public Map<String, AuthData> authDataMap = new HashMap<>();

    public void clear() {
        authDataMap.clear();
    }

    // Auth Data Methods:
    public void createAuth(String authToken, AuthData auth) {
        authDataMap.put(authToken, auth);
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

    public boolean authFound(String authToken) {
        return authDataMap.containsKey(authToken);
    }

    public void putAuth(String name, AuthData auth) {
        authDataMap.put(name, auth);
    }

    public int getAuthSize() {
        return authDataMap.size();
    }

}
