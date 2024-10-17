package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

public class UserService {
    UserDAO userDAO = new UserDAO();
    AuthDAO authDAO = new AuthDAO();

    public AuthData register(UserData user) {
        //first check if the username is already taken
        if (userDAO.userFound(user.username())){
            authDAO.createAuth("auth", user.username());
        }
        //create a new authToken
        //register the new user and then return the new authToken.
        return null;
    }

    public AuthData login(UserData user) {}
    public void logout(AuthData auth) {}
}
