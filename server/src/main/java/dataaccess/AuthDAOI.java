package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public interface AuthDAOI {

    void clear();

    void createAuth(String authToken, AuthData auth);

    AuthData getAuth(String authToken) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    public boolean authFound(String authToken);

    public int getAuthSize();

    public void putAuth(String name, AuthData auth);
}
