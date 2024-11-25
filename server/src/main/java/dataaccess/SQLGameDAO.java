package dataaccess;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import model.GameData;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGameDAO implements GameDAOI{
    //int gameID;
    @Override
    public void clear() {
        var statement = "TRUNCATE game";
        DatabaseManager.clear(statement);
    }

    @Override
    public void createGame(int gameID, GameData gameData) {
        var statement = "INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        try {
            //Serialize the actual game object from the game data to store in database
            String jsonGame = new Gson().toJson(gameData.game());
            //String gameName = new Gson().fromJson(gameData.gameName(), String.class);
            DatabaseManager.executeUpdate(statement, gameID, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), jsonGame);
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
                        //String actualGameName = new Gson().fromJson(gameName, String.class);

                        //deserializes the game from json string stored in database
                        String jsonGame = rs.getString(5);
                        ChessGame game = new Gson().fromJson(jsonGame, ChessGame.class);
                        return new GameData(gameID, whiteUsername, blackUsername, gameName, game, false);
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("connection problem"); //update later
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
                        gameList.add(new GameData(gameID, whiteUsername, blackUsername, gameName, game, false));
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
        return gameList;
    }

    //add an update game method.
    public void updateGame(int gameID, String username, boolean bw) {
        try {
            String statement;
            if (bw) { //white is true black is false
                statement = "UPDATE game SET whiteUsername=? WHERE gameID=?";
            }
            else {
                statement = "UPDATE game SET blackUsername=? WHERE gameID=?";
            }
            DatabaseManager.executeUpdate(statement, username, gameID);
        } catch (DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
    }

    public void updateGameOver(int gameID) {
        try {
            String statement = "UPDATE game SET gameOver=TRUE WHERE gameID=?";
            DatabaseManager.executeUpdate(statement, gameID);
        } catch (DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
    }

    public boolean isGameOver(int gameID) {
        var statement = "SELECT gameOver FROM game WHERE gameID=?";
        return DatabaseManager.getValue(statement, gameID);
    }

    public String getTurn(int gameID) {
        try{
            GameData gameData = getGame(gameID);
            if (gameData.game().getTeamTurn().equals(ChessGame.TeamColor.WHITE)){
                return "WHITE";
            }
            else {
                return "BLACK";
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMove(int gameID, ChessMove move){
        try{
            ChessGame newGame = getGame(gameID).game();
            newGame.makeMove(move);
            String updatedGame = new Gson().toJson(newGame);

            String statement = "UPDATE game SET game=? WHERE gameID=?";
            DatabaseManager.executeUpdate(statement, updatedGame, gameID);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean findGame(int gameID) {
        var statement = "SELECT gameID FROM game WHERE gameID=?";
        return DatabaseManager.found(statement, gameID);
    }

    @Override
    public void putGame(int gameID, GameData game) {
        createGame(gameID, game);
    }

    @Override
    public int getGameSize() {
        var statement = "SELECT gameID FROM game";
        return DatabaseManager.size(statement);
    }
}
