package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameDAO {
    Map<String, GameData> gameDataMap = new HashMap<>();

    // Game Data Methods:
    void createGame(int gameID, String white, String black, String gameName, ChessGame game) {
        gameDataMap.put(gameIDtoString(gameID), new GameData(gameID, white, black, gameName, game));
    }

    public GameData getGame(int gameID) throws DataAccessException {
        String ID = gameIDtoString(gameID);
        if (gameDataMap.containsKey(ID)){
            return gameDataMap.get(ID);
        }
        else{
            throw new DataAccessException("Game ID is incorrect");
        }
    }

    public ArrayList<GameData> listGames() {
        ArrayList<GameData> gameList = new ArrayList<>();
        for (Map.Entry<String, GameData> entry : gameDataMap.entrySet()) {
            gameList.add(entry.getValue());
        }
        return gameList;
    }

    // Needs to be fixed, maybe ask the TAs what I'm supposed to do with this one?? idk.
    void updateGame(int gameID) {
        //Updates a chess game. It should replace the chess game string corresponding to a given gameID.
        // This is used when players join a game or when a move is made.


        String what = gameDataMap.get(gameIDtoString(gameID)).blackUsername();
    }

    public String gameIDtoString(int gameID) {
        return "" + gameID;
    }
}
