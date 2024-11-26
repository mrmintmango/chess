package dataaccess;

import chess.ChessMove;
import model.GameData;

import java.util.ArrayList;

public interface GameDAOI {

    void clear();

    int createGame(GameData gameData);

    GameData getGame(int gameID) throws DataAccessException;

    ArrayList<GameData> listGames();

    boolean findGame(int gameID);

    int getGameSize();

    default void updateGame(int gameID, String username, boolean bw) {

    }

    void updateGameOver(int gameID);

    boolean isGameOver(int gameID);

    void makeMove(int gameID, ChessMove move);

    String getTurn(int gameID);
}
