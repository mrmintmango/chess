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
        gameService = new GameService(authDAO, gameDAO);
    }

    @Test
    public void CreateGameTest() throws DataAccessException {
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.createAuth("testToken", authData);
        gameService.CreateGame(request);
        ChessGame game = gameService.getGame(1111).game();
        GameData gameData = new GameData(1111,null, null, "testName", game);

        Assertions.assertEquals(gameData, gameService.getGame(1111));
    }

    @Test
    public void CreateGameFailTest() {
        boolean thrown = false;

        try {
            gameService.CreateGame(request);
        }
        catch (DataAccessException e) {
            thrown = true;
        }

        Assertions.assertTrue(thrown);
    }


    @Test
    public void JoinGameTestWhite() throws DataAccessException {
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.createAuth("testToken", authData);
        gameService.CreateGame(request);
        JoinGameRequest joinReq = new JoinGameRequest("WHITE", 1111);
        String authToken = "testToken";
        gameService.JoinGame(authToken, joinReq);

        Assertions.assertEquals("authUsername", gameService.getGame(1111).whiteUsername());
    }

    @Test
    public void JoinGameTestBlack() throws DataAccessException {
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.createAuth("testToken", authData);
        gameService.CreateGame(request);
        JoinGameRequest joinReq = new JoinGameRequest("BLACK", 1111);
        String authToken = "testToken";
        gameService.JoinGame(authToken, joinReq);

        Assertions.assertEquals("authUsername", gameService.getGame(1111).blackUsername());
    }

    @Test
    public void JoinGameFailTest() {
        boolean thrown = false;
        JoinGameRequest joinReq = new JoinGameRequest("WHITE", 1111);
        String authToken = "testToken";

        try {
            gameService.JoinGame(authToken, joinReq);
        }
        catch (DataAccessException e) {
            thrown = true;
        }

        Assertions.assertTrue(thrown);
    }


    @Test
    public void ListGamesEmptyTest() throws DataAccessException {
        ArrayList<GameData> expected = new ArrayList<>();
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.createAuth("testToken", authData);
        ArrayList<GameData> actual = gameService.ListGames("testToken");
        
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void ListGamesTest() throws DataAccessException {
        GameData game1 = new GameData(1111, null, null, "game1", new ChessGame());
        GameData game2 = new GameData(1112, null, null, "game2", new ChessGame());
        GameData game3 = new GameData(1113, null, null, "game3", new ChessGame());

        gameService.putGame(1111, game1);
        gameService.putGame(1112, game2);
        gameService.putGame(1113, game3);

        ArrayList<GameData> expected = new ArrayList<>();
        expected.add(game1);
        expected.add(game2);
        expected.add(game3);
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.createAuth("testToken", authData);
        ArrayList<GameData> actual = gameService.ListGames("testToken");

        Assertions.assertEquals(expected, actual);
    }
}
