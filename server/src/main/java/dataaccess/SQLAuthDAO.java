package dataaccess;

import com.google.gson.Gson;
import model.AuthData;

import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAOI{

    @Override
    public void clear() {
        var statement = "TRUNCATE auth";
        DatabaseManager.clear(statement);
    }

    @Override
    public void createAuth(String authToken, AuthData auth) {
        var statement = "INSERT INTO auth (authToken, username) VALUES (?, ?)";
        try {
            DatabaseManager.executeUpdate(statement, authToken, auth.username());
        } catch (DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
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
        var statement = "SELECT authToken FROM auth";
        return DatabaseManager.size(statement);
    }

    @Override
    public void putAuth(String name, AuthData auth) {

    }



}
