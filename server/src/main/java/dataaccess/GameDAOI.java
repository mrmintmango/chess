package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDAOI {

    void clear();

    public void createGame(int gameID, GameData gameData);

    GameData getGame(int gameID) throws DataAccessException;

    ArrayList<GameData> listGames();

    public boolean findGame(int gameID);

    public void putGame(int gameID, GameData game);

    public int getGameSize();

    default void updateGame(int gameID, String username, boolean bw) {

    }
}
