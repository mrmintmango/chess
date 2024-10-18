package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.CreateGameRequest;
import model.GameData;
import model.JoinGameRequest;

import java.util.ArrayList;
import java.util.Objects;

public class GameService {
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;
    //private final UserDAO userDAO;
    public int increment = 0;

    public GameService(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        //this.userDAO = userDAO;
    }

    public ArrayList<GameData> ListGames(String authToken) throws DataAccessException{
        if (authDAO.authFound(authToken)){
            return gameDAO.listGames();
        }
        else throw new DataAccessException("Auth Token not found");


    }

    //double check how to get the gameID
    public ChessGame CreateGame(CreateGameRequest request) throws DataAccessException {
        if (authDAO.authFound(request.authToken())){
            ChessGame game = new ChessGame();
            gameDAO.createGame(increment, null, null, request.gameName(), game);
            increment++;
            return game;
        }
        else throw new DataAccessException("Auth Token not found");
    }

    public void JoinGame(JoinGameRequest request) throws DataAccessException {
        String authToken = request.authToken();
        int gameID = request.gameID();

        if (authDAO.authFound(authToken)){
            String username = authDAO.getAuth(authToken).username();
            if (gameDAO.findGame(gameID)) {
                if (Objects.equals(request.playerColor(), "BLACK"))
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
