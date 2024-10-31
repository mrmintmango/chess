package dataaccess;

import model.GameData;
import model.UserData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGameDAO implements GameDAOI{
    @Override
    public void clear() {
        var statement = "TRUNCATE game";
        DatabaseManager.clear(statement);
    }

    @Override
    public void createGame(int gameID, GameData gameData) {
        var statement = "INSERT INTO game (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        try {
            //Serialize the actual game object from the game data and replace with game
            DatabaseManager.executeUpdate(statement, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), "game");
        } catch (DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game WHERE gameID=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String whiteUsername = rs.getString(2);
                        String blackUsername = rs.getString(3);
                        String gameName = rs.getString(4);
                        //the game will need to be deserialized before becoming an actual game again.
                        String game = rs.getString(5);
                        return new GameData(gameID, whiteUsername, blackUsername, gameName, null);
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
        return null;
    }

    @Override
    public ArrayList<GameData> listGames() {
        return null;
    }

    @Override
    public boolean findGame(int gameID) {
        return false;
    }

    @Override
    public void putGame(int gameID, GameData game) {

    }

    @Override
    public int getGameSize() {
        return 0;
    }
}
