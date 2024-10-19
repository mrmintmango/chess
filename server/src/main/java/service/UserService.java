package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;

import java.util.Objects;
import java.util.UUID;

public class UserService {
    private final AuthDAOI memoryAuthDAO;
    private final UserDAOI memoryUserDAO;

    public UserService(AuthDAOI memoryAuthDAO, UserDAOI memoryUserDAO){
        this.memoryAuthDAO = memoryAuthDAO;
        this.memoryUserDAO = memoryUserDAO;
    }

    public AuthData register(UserData user) throws DataAccessException{
        //first check if the username is already taken
        if (!memoryUserDAO.userFound(user.username())){
            String authToken = UUID.randomUUID().toString(); //creates a new authToken
            AuthData authData = new AuthData(authToken, user.username()); //creates new authData with token and username from user
            memoryAuthDAO.createAuth(authToken, authData); //places the new authData into the authData store
            memoryUserDAO.registerUser(user);
            return authData;
        }
        else throw new DataAccessException("already taken");
    }

    public AuthData login(UserData user) throws DataAccessException {
        if (memoryUserDAO.userFound(user.username())){
            if (Objects.equals(memoryUserDAO.getUser(user.username()).password(), user.password())){
                String authToken = UUID.randomUUID().toString(); //creates a new authToken
                AuthData authData = new AuthData(authToken, user.username()); //creates new authData with token and username from user
                memoryAuthDAO.createAuth(authToken, authData); //places the new authData into the authData store
                return authData;
            }
            else throw new DataAccessException("Incorrect Password");
        }
        else throw new DataAccessException("unauthorized");
    }

    public void logout(String authToken) throws DataAccessException {
        if (memoryAuthDAO.authFound(authToken)){
            memoryAuthDAO.deleteAuth(authToken);
        }
        else throw new DataAccessException("unauthorized");
    }

    //Methods for testing purposes.
    public UserData getUser(String username) throws DataAccessException {
        return memoryUserDAO.getUser(username);
    }

    public void putUser(String name, UserData user) {
        memoryUserDAO.putUser(name, user);
    }

    public void putAuth(String name, AuthData auth) {
        memoryAuthDAO.putAuth(name, auth);
    }

    public int getAuthSize() {
        return memoryAuthDAO.getAuthSize();
    }

}
