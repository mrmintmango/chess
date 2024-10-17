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

}
