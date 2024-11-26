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
        GameData gameData1 = gameService.createGame(request);
        //ChessGame game = gameService.getGame(gameData1.gameID()).game();
        //GameData gameData = new GameData(gameData1.gameID(),null, null, "testName", game, false);

        Assertions.assertFalse(gameService.listGames("testToken").isEmpty());
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
        GameData game1 = new GameData(0, null, null, "game1", new ChessGame(), false);
        GameData game2 = new GameData(1, null, null, "game2", new ChessGame(), false);
        GameData game3 = new GameData(2, null, null, "game3", new ChessGame(), false);

        CreateGameRequest game1req = new CreateGameRequest("auth1", "game1");
        CreateGameRequest game2req = new CreateGameRequest("auth2", "game2");
        CreateGameRequest game3req = new CreateGameRequest("auth3", "game3");

        AuthData authData1 = new AuthData("auth1", "authUsername");
        AuthData authData2 = new AuthData("auth2", "authUsername");
        AuthData authData3 = new AuthData("auth3", "authUsername");
        gameService.createAuth("auth1", authData1);
        gameService.createAuth("auth2", authData2);
        gameService.createAuth("auth3", authData3);


        GameData gameData1 = gameService.createGame(game1req);
        GameData gameData2 = gameService.createGame(game2req);
        GameData gameData3 = gameService.createGame(game3req);

        ArrayList<GameData> expected = new ArrayList<>();
        expected.add(game1);
        expected.add(game2);
        expected.add(game3);
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.createAuth("testToken", authData);
        ArrayList<GameData> actual = gameService.listGames("testToken");

        Assertions.assertEquals(actual.size(), expected.size());
    }
}
