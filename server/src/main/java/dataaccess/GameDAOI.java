package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDAOI {

    public void clear();

    public void createGame(int gameID, String white, String black, String gameName, ChessGame game);

    public GameData getGame(int gameID) throws DataAccessException;

    public ArrayList<GameData> listGames();

    public void updateGame(int gameID, String username, boolean bw);
}
