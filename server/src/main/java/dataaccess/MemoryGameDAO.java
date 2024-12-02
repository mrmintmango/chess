package dataaccess;

import chess.ChessGame;
import chess.ChessMove;
import model.GameData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAOI{
    public Map<Integer, GameData> gameDataMap = new HashMap<>();
    private int gameID = 1;
    SQLGameDAO sqlGameDAO = new SQLGameDAO();

    public void clear() {
        gameDataMap.clear();
    }

    // Game Data Methods:
    public int createGame(GameData gameData) {
        gameDataMap.put(gameID++, gameData);
        return gameID;
    }

    public GameData getGame(int gameID) throws DataAccessException {
        if (gameDataMap.containsKey(gameID)){
            return gameDataMap.get(gameID);
        }
        else{
            throw new DataAccessException("Game ID is incorrect");
        }
    }

    public ArrayList<GameData> listGames() {
        ArrayList<GameData> gameList = new ArrayList<>();
        for (Map.Entry<Integer, GameData> entry : gameDataMap.entrySet()) {
            gameList.add(entry.getValue());
        }
        return gameList;
    }

    public void updateGame(int gameID, String username, boolean bw) {
        GameData original = gameDataMap.get(gameID);
        GameData updatedGame;
        if (bw) { //white is true black is false
            updatedGame = new GameData(original.gameID(), username, original.blackUsername(),
                    original.gameName(), original.game(), false);
        }
        else {
            updatedGame = new GameData(original.gameID(), original.whiteUsername(), username,
                    original.gameName(), original.game(), false);
        }
        gameDataMap.put(gameID, updatedGame);
    }

    public boolean findGame(int gameID) {
        return gameDataMap.containsKey(gameID);
    }

    public int getGameSize() {
        return gameDataMap.size();
    }

    public void updateGameOver(int gameID) {
        GameData original = gameDataMap.get(gameID);
        GameData updatedGame = new GameData(original.gameID(), original.whiteUsername(),
                original.blackUsername(), original.gameName(), original.game(), true);
        gameDataMap.put(gameID, updatedGame);
    }

    public boolean isGameOver(int gameID) {
        return gameDataMap.get(gameID).gameOver();
    }

    public String getTurn(int gameID) {
        return sqlGameDAO.getTurn(gameID);
    }

    public void makeMove(int gameID, ChessMove move){
        try{
            getGame(gameID).game().makeMove(move);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
