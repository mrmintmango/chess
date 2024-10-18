package service;

import chess.ChessGame;
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
        parentService = new ParentService();
    }

    @Test
    public void testClear() {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(5, "white", "black", "gameName", game);
        AuthData authData = new AuthData("authToken", "username");
        UserData userData = new UserData("username", "password", "email@gmail@yahoo");
        parentService.dataAccess.userDataMap.put("username", userData);
        parentService.dataAccess.gameDataMap.put(5, gameData);
        parentService.dataAccess.authDataMap.put("username", authData);

        parentService.ClearApplication();

        Assertions.assertEquals(0, parentService.dataAccess.authDataMap.size());
        Assertions.assertEquals(0, parentService.dataAccess.gameDataMap.size());
        Assertions.assertEquals(0, parentService.dataAccess.userDataMap.size());
    }

}
