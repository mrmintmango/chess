package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Objects;

public class GameService extends ParentService {
    public int increment = 0;


    public ArrayList<GameData> ListGames() {
        return gameDAO.listGames();
    }

    //double check how to get the gameID
    public ChessGame CreateGame(String authToken, String gameName) throws DataAccessException {
        if (authDAO.authFound(authToken)){
            ChessGame game = new ChessGame();
            gameDAO.createGame(increment, null, null, gameName, game);
            increment++;
            return game;
        }
        else throw new DataAccessException("Auth Token not found");
    }

    public void JoinGame(String authToken, String playerColor, int gameID) throws DataAccessException {
        if (authDAO.authFound(authToken)){
            String username = authDAO.getAuth(authToken).username();
            if (gameDAO.findGame(gameID)) {
                if (Objects.equals(playerColor, "BLACK"))
                {
                    gameDAO.updateGame(gameID, username, false);
                }
                else  {
                    gameDAO.updateGame(gameID, username, true);
                }
            }
        }
        else throw new DataAccessException("Auth Token not found");
    }
}
