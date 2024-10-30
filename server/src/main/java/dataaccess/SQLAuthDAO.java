package dataaccess;

import model.AuthData;

import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAOI{

    @Override
    public void clear() {

    }

    @Override
    public void createAuth(String authToken, AuthData auth) {

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public boolean authFound(String authToken) {
        return false;
    }

    @Override
    public int getAuthSize() {
        return 0;
    }

    @Override
    public void putAuth(String name, AuthData auth) {

    }



}
