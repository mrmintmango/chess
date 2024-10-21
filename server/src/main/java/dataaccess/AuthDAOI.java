package dataaccess;

import model.AuthData;

public interface AuthDAOI {

    void clear();

    void createAuth(String authToken, AuthData auth);

    AuthData getAuth(String authToken) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    boolean authFound(String authToken);

    int getAuthSize();

    void putAuth(String name, AuthData auth);
}
