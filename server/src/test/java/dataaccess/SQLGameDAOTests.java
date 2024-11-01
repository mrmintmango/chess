package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SQLGameDAOTests {
    GameDAOI gameDAO = new SQLGameDAO();
    GameData game1 = new GameData(1, null,null, "game1", new ChessGame());
    GameData game2 = new GameData(2, null,null, "game2", new ChessGame());
    GameData game3 = new GameData(3, null,null, "game3", new ChessGame());

    @BeforeEach
    void startup(){
        gameDAO.clear();
        gameDAO.putGame(1, game1);
        gameDAO.putGame(2, game2);
        gameDAO.putGame(3, game3);
    }

    @Test
    public void clearTest() {
        gameDAO.clear();

        Assertions.assertEquals(gameDAO.getGameSize(), 0);
    }

    @Test
    void createGameTest() {
        gameDAO.createGame(4, new GameData(4, null, null, "game4", new ChessGame()));

        Assertions.assertEquals(gameDAO.getGameSize(), 4);
    }

    @Test
    void createGameFailTest() {
        gameDAO.createGame(4, new GameData(4, null, null, "game4", new ChessGame()));

        Assertions.assertNotEquals(gameDAO.getGameSize(), 3);
    }

    @Test
    void getGameTest() throws DataAccessException {
        GameData test = gameDAO.getGame(1);

        Assertions.assertEquals(test.gameName(), "game1");
    }

    @Test
    void getGameFailTest() throws DataAccessException {
        GameData test = gameDAO.getGame(7);

        Assertions.assertEquals(test, null);
    }

    @Test
    void listGamesTest() {
        ArrayList<GameData> list = gameDAO.listGames();

        Assertions.assertEquals(list.size(), 3);
    }

    @Test
    void listGamesFailTest() {
        ArrayList<GameData> list = gameDAO.listGames();

        Assertions.assertNotEquals(list.size(), 4);
    }

    @Test
    void updateGameWhiteTest() throws DataAccessException {
        gameDAO.updateGame(3,"White man", true);

        Assertions.assertEquals(gameDAO.getGame(3).whiteUsername(), "White man");
    }

    @Test
    void updateGameBlackTest() throws DataAccessException {
        gameDAO.updateGame(3,"Black man", false);

        Assertions.assertEquals(gameDAO.getGame(3).blackUsername(), "Black man");
    }

    @Test
    void findGameTest() {
        boolean check = gameDAO.findGame(3);

        Assertions.assertTrue(check);
    }

    @Test
    void findGameFailTest() {
        boolean check = gameDAO.findGame(8);

        Assertions.assertFalse(check);
    }

    @Test
    void putGameTest() {
        gameDAO.putGame(4, new GameData(4, null, null, "game4", new ChessGame()));

        Assertions.assertEquals(gameDAO.getGameSize(), 4);
    }

    @Test
    void putGameFailTest() {
        gameDAO.putGame(4, new GameData(4, null, null, "game4", new ChessGame()));

        Assertions.assertNotEquals(gameDAO.getGameSize(), 3);
    }

    @Test
    void getGameSizeTest() {
        int size = gameDAO.getGameSize();

        Assertions.assertEquals(size, 3);
    }

    @Test
    void getGameSizeFailTest() {
        gameDAO.putGame(4, new GameData(4, null, null, "game4", new ChessGame()));
        int size = gameDAO.getGameSize();

        Assertions.assertNotEquals(size, 3);
    }
}
