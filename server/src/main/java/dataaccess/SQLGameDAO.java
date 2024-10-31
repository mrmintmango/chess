package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
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
            //Serialize the actual game object from the game data to store in database
            String jsonGame = new Gson().toJson(gameData.game());
            DatabaseManager.executeUpdate(statement, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), jsonGame);
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
                        //deserializes the game from json string stored in database
                        String jsonGame = rs.getString(5);
                        ChessGame game = new Gson().fromJson(jsonGame, ChessGame.class);
                        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
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
        ArrayList<GameData> gameList = new ArrayList<>();
        var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game";
        try (var conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int gameID = rs.getInt(1);
                        String whiteUsername = rs.getString(2);
                        String blackUsername = rs.getString(3);
                        String gameName = rs.getString(4);
                        String jsonGame = rs.getString(5);
                        ChessGame game = new Gson().fromJson(jsonGame, ChessGame.class);
                        gameList.add(new GameData(gameID, whiteUsername, blackUsername, gameName, game));
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
        return gameList;
    }

    @Override
    public boolean findGame(int gameID) {
        var statement = "SELECT gameID FROM game WHERE gameID=?";
        return DatabaseManager.found(statement, gameID);
    }

    @Override
    public void putGame(int gameID, GameData game) {

    }

    @Override
    public int getGameSize() {
        return 0;
    }
}
