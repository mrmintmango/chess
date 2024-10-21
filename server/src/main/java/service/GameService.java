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
    public int increment = 1111;

    public GameService(AuthDAOI memoryAuthDAO, GameDAOI memoryGameDAO){
        this.memoryAuthDAO = memoryAuthDAO;
        this.memoryGameDAO = memoryGameDAO;
    }

    public ArrayList<GameData> listGames(String authToken) throws DataAccessException{
        if (memoryAuthDAO.authFound(authToken)){
            return memoryGameDAO.listGames();
        }
        else {
            throw new DataAccessException("unauthorized");
        }
    }

    //double check how to get the gameID
    public GameData createGame(CreateGameRequest request) throws DataAccessException {
        ChessGame game;
        if (request.gameName() != null && request.authToken() != null) {
            if (memoryAuthDAO.authFound(request.authToken())){
                game = new ChessGame();
                GameData gameData = new GameData(increment, null, null, request.gameName(), game);
                memoryGameDAO.createGame(increment, gameData);
                increment++;
                return gameData;
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
            if (memoryAuthDAO.authFound(authToken)){
                String username = memoryAuthDAO.getAuth(authToken).username();
                if (memoryGameDAO.findGame(gameID)) {
                    if (Objects.equals(request.playerColor(), "BLACK") && memoryGameDAO.getGame(gameID).blackUsername() == null)
                    {
                        memoryGameDAO.updateGame(gameID, username, false);
                    }
                    else if (Objects.equals(request.playerColor(), "WHITE") && memoryGameDAO.getGame(gameID).whiteUsername() == null) {
                        memoryGameDAO.updateGame(gameID, username, true);
                    }
                    else if (Objects.equals(request.playerColor(), "WHITE") && !(memoryGameDAO.getGame(gameID).whiteUsername() == null)) {
                        throw new DataAccessException("already taken");
                    }
                    else if (Objects.equals(request.playerColor(), "BLACK") && !(memoryGameDAO.getGame(gameID).blackUsername() == null)) {
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
        memoryAuthDAO.createAuth(name, auth);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return memoryGameDAO.getGame(gameID);
    }

    public void putGame(int gameID, GameData game) {
        memoryGameDAO.putGame(gameID, game);
    }
}
