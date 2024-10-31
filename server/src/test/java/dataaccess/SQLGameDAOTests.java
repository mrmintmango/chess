package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
