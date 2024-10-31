package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SQLUserDAOTests {
    UserDAOI userDAO = new SQLUserDAO();
    UserData user1 = new UserData("User1", "Password1", "Email1");
    UserData user2 = new UserData("User2", "Password2", "Email2");
    UserData user3 = new UserData("User3", "Password3", "Email3");

    public SQLUserDAOTests() throws DataAccessException {
    }

    @BeforeEach
    public void setUserDAO() {
        userDAO.clear();
        userDAO.registerUser(user1);
        userDAO.registerUser(user2);
        userDAO.registerUser(user3);
    }

    @Test
    public void clearTest() {
        userDAO.clear();

        Assertions.assertEquals(userDAO.getUserSize(), 0);
    }

    @Test
    public void getUserTest() {
        UserData check;
        try {
            check = userDAO.getUser("User1");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(user1.username(), check.username());
    }

    @Test
    public void getUserFailTest() throws DataAccessException {
        DataAccessException thrown = null;
        UserData check;
        try {
            check = userDAO.getUser("UserOne");
        } catch (DataAccessException e) {
            thrown = new DataAccessException("wrong username");
        }

        assert thrown != null;
        Assertions.assertEquals("wrong username", thrown.getMessage());
    }

    @Test
    public void userFoundTest() {
        boolean find = userDAO.userFound("User3");

        Assertions.assertTrue(find);
    }

    @Test
    public void userFoundFailTest() {
        boolean find = userDAO.userFound("UserThree");

        Assertions.assertFalse(find);
    }

    @Test
    public void putUserTest() {
        userDAO.putUser("User4", new UserData("User4", "pass4", "gmail4"));
        boolean find = userDAO.userFound("User4");

        Assertions.assertTrue(find);
    }

    @Test
    public void putUserFailTest() {
        userDAO.putUser("User4", new UserData("User4", "pass4", "gmail4"));
        boolean find = userDAO.userFound("UserFour");
        //No idea how to actually test for this method not working
        Assertions.assertFalse(find);
    }

    @Test
    public void registerUserTest() {
        userDAO.registerUser(new UserData("User4", "pass4", "gmail4"));
        boolean find = userDAO.userFound("User4");

        Assertions.assertTrue(find);
    }

    @Test
    public void registerUserFailTest() {
        userDAO.registerUser(new UserData("User4", "pass4", "gmail4"));
        boolean find = userDAO.userFound("UserFour");

        Assertions.assertFalse(find);
    }

    @Test
    public void getUserSizeTest() {
        int size = userDAO.getUserSize();

        Assertions.assertEquals(3, size);
    }

    @Test
    public void getUserSizeFailTest() {
        int size = userDAO.getUserSize();

        Assertions.assertNotSame(4, size);
    }
}
