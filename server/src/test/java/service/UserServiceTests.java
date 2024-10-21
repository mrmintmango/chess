package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTests {
    private UserService userService;

    @BeforeEach
    public void setUserService() {
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        userService = new UserService(authDAO, userDAO);
    }

    @Test
    public void registerTest() throws DataAccessException {
        UserData user = new UserData("username", "password", "email@gmail.com");
        userService.register(user);

        Assertions.assertEquals(user, userService.getUser("username"));
    }

    @Test
    public void registerTakenUsername() throws DataAccessException {
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
    public void LoginTest() throws DataAccessException {
        UserData user1 = new UserData("user1", "pass", "fake email");
        UserData user2 = new UserData("user2", "password", "fake-email");
        userService.putUser("user1",user1);
        userService.putUser("user2",user2);

        AuthData expected = new AuthData("random", "user1");
        AuthData actual = userService.login(user1);
        Assertions.assertEquals(expected.username(), actual.username());
    }

    @Test
    public void LoginFailPasswordTest() throws DataAccessException {
        boolean thrown = false;
        UserData user1 = new UserData("user1", "pass", "fake email");
        UserData user2 = new UserData("user2", "password", "fake-email");
        userService.putUser("user1",user1);
        userService.putUser("user2",user2);
        UserData wrong = new UserData("user1", "word", "fake email");

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
    public void LoginFailUsernameTest() throws DataAccessException {
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
    public void logoutFailTest() throws DataAccessException {
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
