package dataaccess;


import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DataAccess {
    Map<String, AuthData> authDataMap = new HashMap<>();
    Map<String, GameData> gameDataMap = new HashMap<>();
    Map<String, UserData> userDataMap = new HashMap<>();
    public DataAccess() {

    }

    void clear() {
        authDataMap.clear();
        gameDataMap.clear();
        userDataMap.clear();
    }

    // User Data Methods:
    void createUser(String username, String password, String email) {
        userDataMap.put(username, new UserData(username, password, email));
    }

    // Game Data Methods:
    void createGame(int gameID, String white, String black, String gameName, ChessGame game) {
        gameDataMap.put(gameName, new GameData(gameID, white, black, gameName, game));
    }

    // Auth Data Methods:
    void createAuth(String authToken, String username) {
        authDataMap.put(username, new AuthData(authToken, username));
    }

}
