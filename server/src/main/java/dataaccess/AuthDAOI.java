package dataaccess;

import model.AuthData;

public interface AuthDAOI {

    public void clear();

    public void createAuth(String authToken, AuthData auth);

    public AuthData getAuth(String authToken) throws DataAccessException;

    public void deleteAuth(String authToken) throws DataAccessException;
}
