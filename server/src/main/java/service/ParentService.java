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

        //this.memoryAuthDAO = (MemoryAuthDAO) memoryAuthDAO;
        //this.memoryGameDAO = (MemoryGameDAO) memoryGameDAO;
        //this.memoryUserDAO = (MemoryUserDAO) memoryUserDAO;
        //change all to interfaces
    }

    public void ClearApplication() {
        memoryAuthDAO.clear();
        memoryGameDAO.clear();
        memoryUserDAO.clear();
    }

    //FOR TESTING

    // public void AddAuth(String authToken, AuthData auth) {
    //     memoryAuthDAO.authDataMap.put(authToken, auth);
    // }
    // public void AddGame(int gameID, GameData game) {
    //     memoryGameDAO.gameDataMap.put(gameID, game);
    // }
    // public void AddUser(String username, UserData user) {
    //     memoryUserDAO.userDataMap.put(username, user);
    // }
    //
    // public int authSize() {
    //     return memoryAuthDAO.authDataMap.size();
    // }
    // public int userSize() {
    //     return memoryUserDAO.userDataMap.size();
    // }
    // public int gameSize() {
    //     return memoryGameDAO.gameDataMap.size();
    // }
}
