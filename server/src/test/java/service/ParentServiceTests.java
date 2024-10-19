package service;

import chess.ChessGame;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ParentServiceTests {
    private ParentService parentService;

    @BeforeEach
    public void setUp() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
        MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
        parentService = new ParentService(memoryAuthDAO, memoryGameDAO, memoryUserDAO);
    }

    @Test
    public void testClear() {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(5, "white", "black", "gameName", game);
        AuthData authData = new AuthData("authToken", "username");
        UserData userData = new UserData("username", "password", "email@gmail@yahoo");
        parentService.AddUser("username", userData);
        parentService.AddGame(5, gameData);
        parentService.AddAuth("username", authData);

        parentService.ClearApplication();

        Assertions.assertEquals(0, parentService.authSize());
        Assertions.assertEquals(0, parentService.gameSize());
        Assertions.assertEquals(0, parentService.userSize());
    }

}
