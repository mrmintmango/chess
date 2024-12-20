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
    private final AuthDAOI authDAO;
    private final GameDAOI gameDAO;
    public int increment;

    public GameService(AuthDAOI authDAO, GameDAOI gameDAO, int incrementVal){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        increment = incrementVal;
    }

    public ArrayList<GameData> listGames(String authToken) throws DataAccessException{
        if (authDAO.authFound(authToken)){
            return gameDAO.listGames();
        }
        else {
            throw new DataAccessException("unauthorized");
        }
    }

    //double check how to get the gameID
    public GameData createGame(CreateGameRequest request) throws DataAccessException {
        ChessGame game;
        if (request.gameName() != null && request.authToken() != null) {
            if (authDAO.authFound(request.authToken())){
                game = new ChessGame();
                //the zeros used to be the increment value
                GameData gameData = new GameData(0,null, null, request.gameName(), game, false);
                int gameID = gameDAO.createGame(gameData);
                return new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(),
                        gameData.gameName(), gameData.game(), gameData.gameOver());
            }
            else {
                throw new DataAccessException("unauthorized");
            }
        }
        else {
            throw new DataAccessException("bad request");
        }
    }

    public void joinGame(String authToken, JoinGameRequest request) throws DataAccessException {
        int gameID = request.gameID();
        if (request.playerColor() != null){
            if (authDAO.authFound(authToken)){
                String username = authDAO.getAuth(authToken).username();
                if (gameDAO.findGame(gameID)) {
                    if (Objects.equals(request.playerColor(), "BLACK") && gameDAO.getGame(gameID).blackUsername() == null)
                    {
                        gameDAO.updateGame(gameID, username, false);
                    }
                    else if (Objects.equals(request.playerColor(), "WHITE") && gameDAO.getGame(gameID).whiteUsername() == null) {
                        gameDAO.updateGame(gameID, username, true);
                    }
                    else if (Objects.equals(request.playerColor(), "WHITE") && !(gameDAO.getGame(gameID).whiteUsername() == null)) {
                        throw new DataAccessException("already taken");
                    }
                    else if (Objects.equals(request.playerColor(), "BLACK") && !(gameDAO.getGame(gameID).blackUsername() == null)) {
                        throw new DataAccessException("already taken");
                    }
                }
                else {
                    throw new DataAccessException("bad request");
                }
            }
            else {
                throw new DataAccessException("unauthorized");
            }
        }
        else {
            throw new DataAccessException("bad request");
        }
    }

    public void createAuth(String name, AuthData auth) {
        authDAO.createAuth(name, auth);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return gameDAO.getGame(gameID);
    }

}
