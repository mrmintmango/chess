package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    //if you are going to test the CreateGame method for throwing exceptions use asserExceptions.

    @Test
    public void JoinGameTest() throws DataAccessException {
        AuthData authData = new AuthData("testToken", "authUsername");
        gameService.authDAO.createAuth("testToken", authData);
        gameService.CreateGame("testToken", "testName");
        gameService.JoinGame("testToken","WHITE", 0);

        Assertions.assertEquals("authUsername", gameService.gameDAO.getGame(0).whiteUsername());
    }

    @Test
    public void ListGamesTest() {}
}
