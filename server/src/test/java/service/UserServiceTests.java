package service;


import dataaccess.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTests {
    private UserService userService;

    @BeforeEach
    public void setUserService() {
        userService = new UserService();
    }

    @Test
    public void registerTest() throws DataAccessException {
        UserData user = new UserData("username", "password", "email@gmail.com");
        userService.register(user);


        Assertions.assertEquals(user, userService.userDAO.getUser("username"));
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


}
