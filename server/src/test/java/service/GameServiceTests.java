package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GameServiceTests {
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        gameService = new GameService();
    }

    @Test
    public void CreateGameTest() throws DataAccessException {
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.authDAO.createAuth("testToken", authData);
        gameService.CreateGame("testToken", "testName");
        ChessGame game = gameService.gameDAO.gameDataMap.get(0).game();
        GameData gameData = new GameData(0,null, null, "testName", game);

        Assertions.assertEquals(gameData, gameService.gameDAO.gameDataMap.get(0));
    }

    @Test
    public void CreateGameFailTest() throws DataAccessException {
        boolean thrown = false;

        try {
            gameService.CreateGame("testToken", "testName");
        }
        catch (DataAccessException e) {
            thrown = true;
        }

        Assertions.assertTrue(thrown);
    }


    @Test
    public void JoinGameTestWhite() throws DataAccessException {
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.authDAO.createAuth("testToken", authData);
        gameService.CreateGame("testToken", "testName");
        gameService.JoinGame("testToken","WHITE", 0);

        Assertions.assertEquals("authUsername", gameService.gameDAO.getGame(0).whiteUsername());
    }

    @Test
    public void JoinGameTestBlack() throws DataAccessException {
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.authDAO.createAuth("testToken", authData);
        gameService.CreateGame("testToken", "testName");
        gameService.JoinGame("testToken","BLACK", 0);

        Assertions.assertEquals("authUsername", gameService.gameDAO.getGame(0).blackUsername());
    }

    @Test
    public void JoinGameFailTest() throws DataAccessException {
        boolean thrown = false;

        try {
            gameService.JoinGame("testToken","WHITE", 0);
        }
        catch (DataAccessException e) {
            thrown = true;
        }
    }


    @Test
    public void ListGamesEmptyTest() {
        ArrayList<GameData> expected = new ArrayList<>();
        ArrayList<GameData> actual = gameService.ListGames();
        
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void ListGamesTest() {
        GameData game1 = new GameData(0, null, null, "game1", new ChessGame());
        GameData game2 = new GameData(1, null, null, "game2", new ChessGame());
        GameData game3 = new GameData(2, null, null, "game3", new ChessGame());

        gameService.gameDAO.gameDataMap.put(0, game1);
        gameService.gameDAO.gameDataMap.put(1, game2);
        gameService.gameDAO.gameDataMap.put(2, game3);

        ArrayList<GameData> expected = new ArrayList<>();
        expected.add(game1);
        expected.add(game2);
        expected.add(game3);
        ArrayList<GameData> actual = gameService.ListGames();

        Assertions.assertEquals(expected, actual);
    }
}
