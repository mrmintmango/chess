package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GameServiceTests {
    private GameService gameService;
    CreateGameRequest request = new CreateGameRequest("testToken", "testName");

    @BeforeEach
    public void setUp() {
        MemoryGameDAO gameDAO = new MemoryGameDAO();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        gameService = new GameService(authDAO, gameDAO, gameDAO.getGameSize()+1);
    }

    @Test
    public void createGameTest() throws DataAccessException {
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.createAuth("testToken", authData);
        gameService.createGame(request);
        ChessGame game = gameService.getGame(1).game();
        GameData gameData = new GameData(1,null, null, "testName", game, false);

        Assertions.assertEquals(gameData, gameService.getGame(1));
    }

    @Test
    public void createGameFailTest() {
        boolean thrown = false;

        try {
            gameService.createGame(request);
        }
        catch (DataAccessException e) {
            thrown = true;
        }

        Assertions.assertTrue(thrown);
    }


    @Test
    public void joinGameTestWhite() throws DataAccessException {
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.createAuth("testToken", authData);
        gameService.createGame(request);
        JoinGameRequest joinReq = new JoinGameRequest("WHITE", 1);
        String authToken = "testToken";
        gameService.joinGame(authToken, joinReq);

        Assertions.assertEquals("authUsername", gameService.getGame(1).whiteUsername());
    }

    @Test
    public void joinGameTestBlack() throws DataAccessException {
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.createAuth("testToken", authData);
        gameService.createGame(request);
        JoinGameRequest joinReq = new JoinGameRequest("BLACK", 1);
        String authToken = "testToken";
        gameService.joinGame(authToken, joinReq);

        Assertions.assertEquals("authUsername", gameService.getGame(1).blackUsername());
    }

    @Test
    public void joinGameFailTest() {
        boolean thrown = false;
        JoinGameRequest joinReq = new JoinGameRequest("WHITE", 1);
        String authToken = "testToken";

        try {
            gameService.joinGame(authToken, joinReq);
        }
        catch (DataAccessException e) {
            thrown = true;
        }

        Assertions.assertTrue(thrown);
    }


    @Test
    public void listGamesEmptyTest() throws DataAccessException {
        ArrayList<GameData> expected = new ArrayList<>();
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.createAuth("testToken", authData);
        ArrayList<GameData> actual = gameService.listGames("testToken");
        
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void listGamesTest() throws DataAccessException {
        GameData game1 = new GameData(1, null, null, "game1", new ChessGame(), false);
        GameData game2 = new GameData(2, null, null, "game2", new ChessGame(), false);
        GameData game3 = new GameData(3, null, null, "game3", new ChessGame(), false);

        gameService.putGame(1, game1);
        gameService.putGame(2, game2);
        gameService.putGame(3, game3);

        ArrayList<GameData> expected = new ArrayList<>();
        expected.add(game1);
        expected.add(game2);
        expected.add(game3);
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.createAuth("testToken", authData);
        ArrayList<GameData> actual = gameService.listGames("testToken");

        Assertions.assertEquals(expected, actual);
    }
}
