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
    private final AuthDAOI AuthDAO;
    private final GameDAOI GameDAO;
    public int increment = 1;

    public GameService(AuthDAOI AuthDAO, GameDAOI GameDAO){
        this.AuthDAO = AuthDAO;
        this.GameDAO = GameDAO;
    }

    public ArrayList<GameData> listGames(String authToken) throws DataAccessException{
        if (AuthDAO.authFound(authToken)){
            return GameDAO.listGames();
        }
        else {
            throw new DataAccessException("unauthorized");
        }
    }

    //double check how to get the gameID
    public GameData createGame(CreateGameRequest request) throws DataAccessException {
        ChessGame game;
        if (request.gameName() != null && request.authToken() != null) {
            if (AuthDAO.authFound(request.authToken())){
                game = new ChessGame();
                //the zeros used to be the increment value
                GameData gameData = new GameData(increment, null, null, request.gameName(), game);
                GameDAO.createGame(increment, gameData);
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
            if (AuthDAO.authFound(authToken)){
                String username = AuthDAO.getAuth(authToken).username();
                if (GameDAO.findGame(gameID)) {
                    if (Objects.equals(request.playerColor(), "BLACK") && GameDAO.getGame(gameID).blackUsername() == null)
                    {
                        GameDAO.updateGame(gameID, username, false);
                    }
                    else if (Objects.equals(request.playerColor(), "WHITE") && GameDAO.getGame(gameID).whiteUsername() == null) {
                        GameDAO.updateGame(gameID, username, true);
                    }
                    else if (Objects.equals(request.playerColor(), "WHITE") && !(GameDAO.getGame(gameID).whiteUsername() == null)) {
                        throw new DataAccessException("already taken");
                    }
                    else if (Objects.equals(request.playerColor(), "BLACK") && !(GameDAO.getGame(gameID).blackUsername() == null)) {
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
        AuthDAO.createAuth(name, auth);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return GameDAO.getGame(gameID);
    }

    public void putGame(int gameID, GameData game) {
        GameDAO.putGame(gameID, game);
    }
}
