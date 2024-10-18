package dataaccess;


import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DataAccess {
    public Map<String, AuthData> authDataMap = new HashMap<>();
    public Map<Integer, GameData> gameDataMap = new HashMap<>();
    public Map<String, UserData> userDataMap = new HashMap<>();
    public DataAccess() {

    }

    public void clear() {
        authDataMap.clear();
        gameDataMap.clear();
        userDataMap.clear();
    }

}
