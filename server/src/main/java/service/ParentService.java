package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;

public class ParentService {

    private final AuthDAO authDAO;
    private final GameDAO gameDAO;
    private final UserDAO userDAO;

    public ParentService(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    public void ClearApplication() {
        authDAO.clear();
        gameDAO.clear();
        userDAO.clear();
    }

    public void AddAuth(String authToken, AuthData auth) {
        authDAO.authDataMap.put(authToken, auth);
    }
    public void AddGame(int gameID, GameData game) {
        gameDAO.gameDataMap.put(gameID, game);
    }
    public void AddUser(String username, UserData user) {
        userDAO.userDataMap.put(username, user);
    }

    public int authSize() {
        return authDAO.authDataMap.size();
    }
    public int userSize() {
        return userDAO.userDataMap.size();
    }
    public int gameSize() {
        return gameDAO.gameDataMap.size();
    }
}
