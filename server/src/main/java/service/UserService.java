package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.Objects;
import java.util.UUID;

public class UserService extends ParentService {


    public AuthData register(UserData user) throws DataAccessException{
        //first check if the username is already taken
        if (!userDAO.userFound(user.username())){
            String authToken = UUID.randomUUID().toString(); //creates a new authToken
            AuthData authData = new AuthData(authToken, user.username()); //creates new authData with token and username from user
            authDAO.createAuth(authToken, authData); //places the new authData into the authData store
            userDAO.registerUser(user);
            return authData;
        }
        else throw new DataAccessException("Username already taken");
    }

    public AuthData login(UserData user) throws DataAccessException {
        if (userDAO.userFound(user.username())){
            if (Objects.equals(userDAO.getUser(user.username()).password(), user.password())){
                String authToken = UUID.randomUUID().toString(); //creates a new authToken
                AuthData authData = new AuthData(authToken, user.username()); //creates new authData with token and username from user
                authDAO.createAuth(authToken, authData); //places the new authData into the authData store
                return authData;
            }
            else throw new DataAccessException("Incorrect Password");
        }
        else throw new DataAccessException("Username not found");
    }

    public void logout(AuthData auth) throws DataAccessException {
        if (authDAO.authFound(auth.authToken())){
            authDAO.deleteAuth(auth.authToken());
        }
        else throw new DataAccessException("Auth not found");
    }
}
