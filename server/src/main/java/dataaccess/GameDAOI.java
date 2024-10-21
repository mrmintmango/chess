package dataaccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAOI {

    void clear();

    void createGame(int gameID, GameData gameData);

    GameData getGame(int gameID) throws DataAccessException;

    ArrayList<GameData> listGames();

    boolean findGame(int gameID);

    void putGame(int gameID, GameData game);

    int getGameSize();

    default void updateGame(int gameID, String username, boolean bw) {

    }
}
