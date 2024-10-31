package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.UUID;

public class UserService {
    private final AuthDAOI authDAO;
    private final UserDAOI userDAO;

    public UserService(AuthDAOI authDAO, UserDAOI userDAO){
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public AuthData register(UserData user) throws DataAccessException{
        if (user.username() != null && user.password() != null) {
            if (!userDAO.userFound(user.username())){
                String authToken = UUID.randomUUID().toString(); //creates a new authToken
                AuthData authData = new AuthData(authToken, user.username()); //creates new authData with token and username from user
                authDAO.createAuth(authToken, authData); //places the new authData into the authData store
                userDAO.registerUser(user);
                return authData;
            }
            else {
                throw new DataAccessException("already taken");
            }
        }
        else {
            throw new DataAccessException("bad request");
        }
    }

    public AuthData login(UserData user) throws DataAccessException {
        if (userDAO.userFound(user.username())){
            //Objects.equals(userDAO.getUser(user.username()).password(), user.password())
            //That was the previous if before implementing SQL
            //String hashedPass = BCrypt.hashpw(user.password(), BCrypt.gensalt());
            if (BCrypt.checkpw(user.password(), userDAO.getUser(user.username()).password())){
                String authToken = UUID.randomUUID().toString(); //creates a new authToken
                AuthData authData = new AuthData(authToken, user.username()); //creates new authData with token and username from user
                authDAO.createAuth(authToken, authData); //places the new authData into the authData store
                return authData;
            }
            else {
                throw new DataAccessException("Incorrect Password"); //Add int to identify error
            }
        }
        else {
            throw new DataAccessException("unauthorized");
        }
    }

    public void logout(String authToken) throws DataAccessException {
        if (authDAO.authFound(authToken)){
            authDAO.deleteAuth(authToken);
        }
        else {
            throw new DataAccessException("unauthorized");
        }
    }

    //Methods for testing purposes.
    public UserData getUser(String username) throws DataAccessException {
        return userDAO.getUser(username);
    }

    public void putUser(String name, UserData user) {
        userDAO.putUser(name, user);
    }

    public void putAuth(String name, AuthData auth) {
        authDAO.putAuth(name, auth);
    }

    public int getAuthSize() {
        return authDAO.getAuthSize();
    }

}
