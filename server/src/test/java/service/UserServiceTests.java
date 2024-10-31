package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceTests {
    private UserService userService;
    UserDAOI userDAO = new SQLUserDAO();
    AuthDAOI authDAO = new SQLAuthDAO();

    public UserServiceTests() throws DataAccessException {
        userService = new UserService(authDAO, userDAO);
    }

    @BeforeEach
    public void setUserService() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
    }

    @Test
    public void registerTest() throws DataAccessException {
        UserData user = new UserData("username", "password", "email@gmail.com");
        userService.register(user);
        boolean match = BCrypt.checkpw(user.password(), userDAO.getUser(user.username()).password());

        Assertions.assertTrue(match);
    }

    @Test
    public void registerTakenUsername() {
        boolean thrown = false;
        try {
            UserData user1 = new UserData("username", "password", "email@gmail.com");
            UserData user2 = new UserData("username", "password", "email@gmail.com");
            userService.register(user1);
            userService.register(user2);
        }
        catch (DataAccessException e) {
            thrown = true;
        }

        Assertions.assertTrue(thrown);
    }

    @Test
    public void loginTest() throws DataAccessException {
        UserData user1 = new UserData("user1", "pass", "fake email");
        UserData user2 = new UserData("user2", "password", "fake-email");
        userService.putUser("user1",user1);
        userService.putUser("user2",user2);

        AuthData expected = new AuthData("random", "user1");
        AuthData actual = userService.login(user1);
        Assertions.assertEquals(expected.username(), actual.username());
    }

    @Test
    public void loginFailPasswordTest() {
        boolean thrown = false;
        UserData user1 = new UserData("user1", "pass", "fake email");
        UserData user2 = new UserData("user2", "password", "fake-email");
        userService.putUser("user1",user1);
        userService.putUser("user2",user2);
        UserData wrong = new UserData("user1", "word", "fake email");

        try {
            AuthData actual = userService.login(wrong);
        }
        catch (DataAccessException e) {
            thrown = true;
        }

        Assertions.assertTrue(thrown);
    }

    @Test
    public void loginFailUsernameTest() {
        boolean thrown = false;
        UserData user1 = new UserData("user1", "pass", "fake email");
        UserData user2 = new UserData("user2", "password", "fake-email");
        userService.putUser("user1",user1);
        userService.putUser("user2",user2);
        UserData wrong = new UserData("user10", "pass", "fake email");

        //AuthData expected = new AuthData("random", "user1");
        try {
            AuthData actual = userService.login(wrong);
        }
        catch (DataAccessException e) {
            thrown = true;
        }

        Assertions.assertTrue(thrown);
    }


    @Test
    public void logoutTest() throws DataAccessException {
        AuthData authData = new AuthData("token", "username");
        userService.putAuth("token", authData);

        userService.logout(authData.authToken());

        Assertions.assertEquals(0, userService.getAuthSize());
    }

    @Test
    public void logoutFailTest() {
        boolean thrown = false;
        AuthData authData = new AuthData("token", "username");
        userService.putAuth("token", authData);
        AuthData wrong = new AuthData("broken", "username");

        try {
            userService.logout(wrong.authToken());
        }
        catch (DataAccessException e) {
            thrown = true;
        }

        Assertions.assertTrue(thrown);
    }


}
