package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        var statement = "SELECT authToken, username FROM auth WHERE authToken=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String username = rs.getString(2);
                        return new AuthData(authToken, username);
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authToken=?";
        DatabaseManager.executeUpdate(statement, authToken);
    }

    @Override
    public boolean authFound(String authToken) {
        var statement = "SELECT authToken FROM auth WHERE authToken=?";
        return DatabaseManager.found(statement, authToken);
    }

    @Override
    public int getAuthSize() {
        var statement = "SELECT authToken FROM auth";
        return DatabaseManager.size(statement);
    }

    @Override
    public void putAuth(String name, AuthData auth) {
        createAuth(auth.authToken(), auth);
    }



}
