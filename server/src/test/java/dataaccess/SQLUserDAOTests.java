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

        Assertions.assertEquals(user1, check);
    }

    @Test
    public void getUserFailTest() throws DataAccessException {
        UserData check;
        try {
            check = userDAO.getUser("UserOne");
        } catch (DataAccessException e) {
            throw new DataAccessException("wrong username");
        }

        //Assertions.assertThrows(DataAccessException, e);
    }
}
