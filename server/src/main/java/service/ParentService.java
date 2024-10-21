package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;

public class ParentService {

    private final AuthDAOI memoryAuthDAO;
    private final GameDAOI memoryGameDAO;
    private final UserDAOI memoryUserDAO;

    public ParentService(AuthDAOI memoryAuthDAO, GameDAOI memoryGameDAO, UserDAOI memoryUserDAO) {
        this.memoryGameDAO = memoryGameDAO;
        this.memoryAuthDAO = memoryAuthDAO;
        this.memoryUserDAO = memoryUserDAO;
    }

    public void clearApplication() {
        memoryAuthDAO.clear();
        memoryGameDAO.clear();
        memoryUserDAO.clear();
    }

    //FOR TESTING

     public void addAuth(String authToken, AuthData auth) {
        memoryAuthDAO.createAuth(authToken,auth);
     }
     public void addGame(int gameID, GameData game) {
        memoryGameDAO.putGame(gameID, game);
     }
     public void addUser(String username, UserData user) {
        memoryUserDAO.putUser(username, user);
     }

     public int authSize() {
         return memoryAuthDAO.getAuthSize();
     }
     public int userSize() {
         return memoryUserDAO.getUserSize();
     }
     public int gameSize() {
         return memoryGameDAO.getGameSize();
     }
}
