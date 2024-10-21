package dataaccess;

import model.GameData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAOI{
    public Map<Integer, GameData> gameDataMap = new HashMap<>();

    public void clear() {
        gameDataMap.clear();
    }

    // Game Data Methods:
    public void createGame(int gameID, GameData gameData) {
        gameDataMap.put(gameID, gameData);
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
            updatedGame = new GameData(original.gameID(), username, original.blackUsername(), original.gameName(), original.game());
        }
        else {
            updatedGame = new GameData(original.gameID(), original.whiteUsername(), username, original.gameName(), original.game());
        }
        gameDataMap.put(gameID, updatedGame);
    }

    public boolean findGame(int gameID) {
        return gameDataMap.containsKey(gameID);
    }

    public void putGame(int gameID, GameData game) {
        gameDataMap.put(gameID, game);
    }

    public int getGameSize() {
        return gameDataMap.size();
    }

}
