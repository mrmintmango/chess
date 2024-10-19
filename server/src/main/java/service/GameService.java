package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.CreateGameRequest;
import model.GameData;
import model.JoinGameRequest;

import java.util.ArrayList;
import java.util.Objects;

public class GameService {
    private final AuthDAOI memoryAuthDAO;
    private final GameDAOI memoryGameDAO;
    public int increment = 0;

    public GameService(AuthDAOI memoryAuthDAO, GameDAOI memoryGameDAO){
        this.memoryAuthDAO = memoryAuthDAO;
        this.memoryGameDAO = memoryGameDAO;
    }

    public ArrayList<GameData> ListGames(String authToken) throws DataAccessException{
        if (memoryAuthDAO.authFound(authToken)){
            return memoryGameDAO.listGames();
        }
        else throw new DataAccessException("Auth Token not found");


    }

    //double check how to get the gameID
    public ChessGame CreateGame(CreateGameRequest request) throws DataAccessException {
        if (memoryAuthDAO.authFound(request.authToken())){
            ChessGame game = new ChessGame();
            memoryGameDAO.createGame(increment, null, null, request.gameName(), game);
            increment++;
            return game;
        }
        else throw new DataAccessException("Auth Token not found");
    }

    public void JoinGame(String authToken, JoinGameRequest request) throws DataAccessException {
        int gameID = request.gameID();

        if (memoryAuthDAO.authFound(authToken)){
            String username = memoryAuthDAO.getAuth(authToken).username();
            if (memoryGameDAO.findGame(gameID)) {
                if (Objects.equals(request.playerColor(), "BLACK"))
                {
                    memoryGameDAO.updateGame(gameID, username, false);
                }
                else  {
                    memoryGameDAO.updateGame(gameID, username, true);
                }
            }
        }
        else throw new DataAccessException("Auth Token not found");
    }

    public void createAuth(String name, AuthData auth) {
        memoryAuthDAO.createAuth(name, auth);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return memoryGameDAO.getGame(gameID);
    }

    public void putGame(int gameID, GameData game) {
        memoryGameDAO.putGame(gameID, game);
    }
}
