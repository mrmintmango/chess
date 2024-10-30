package dataaccess;

import model.GameData;

import java.util.ArrayList;

public class SQLGameDAO implements GameDAOI{
    @Override
    public void clear() {

    }

    @Override
    public void createGame(int gameID, GameData gameData) {

    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public ArrayList<GameData> listGames() {
        return null;
    }

    @Override
    public boolean findGame(int gameID) {
        return false;
    }

    @Override
    public void putGame(int gameID, GameData game) {

    }

    @Override
    public int getGameSize() {
        return 0;
    }
}
