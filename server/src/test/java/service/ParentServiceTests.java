package service;

import chess.ChessGame;
import dataaccess.*;
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
        AuthDAOI memoryAuthDAO = new MemoryAuthDAO();
        GameDAOI memoryGameDAO = new MemoryGameDAO();
        UserDAOI memoryUserDAO = new MemoryUserDAO();
        parentService = new ParentService(memoryAuthDAO, memoryGameDAO, memoryUserDAO);
    }

    @Test
    public void testClear() {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(5, "white", "black", "gameName", game);
        AuthData authData = new AuthData("authToken", "username");
        UserData userData = new UserData("username", "password", "email@gmail@yahoo");
        parentService.addUser("username", userData);
        parentService.addGame(5, gameData);
        parentService.addAuth("username", authData);

        parentService.clearApplication();

        Assertions.assertEquals(0, parentService.authSize());
        Assertions.assertEquals(0, parentService.gameSize());
        Assertions.assertEquals(0, parentService.userSize());
    }

}
