package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDAOI {

    void clear();

    void createGame(int gameID, String white, String black, String gameName, ChessGame game);

    GameData getGame(int gameID) throws DataAccessException;

    ArrayList<GameData> listGames();

    public boolean findGame(int gameID);

    public void putGame(int gameID, GameData game);

    default void updateGame(int gameID, String username, boolean bw) {

    }
}
