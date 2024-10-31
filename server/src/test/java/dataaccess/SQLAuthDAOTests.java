package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SQLAuthDAOTests {
    AuthDAOI authDAO = new SQLAuthDAO();
    AuthData auth1 = new AuthData("AuthToken1", "user1");
    AuthData auth2 = new AuthData("AuthToken2", "user2");
    AuthData auth3 = new AuthData("AuthToken3", "user3");

    @BeforeEach
    void startup(){
        authDAO.clear();
        authDAO.putAuth("User1", auth1);
        authDAO.putAuth("User2", auth2);
        authDAO.putAuth("User3", auth3);
    }

    @Test
    public void clearTest() {
        authDAO.clear();

        Assertions.assertEquals(authDAO.getAuthSize(), 0);
    }

    @Test
    void createAuthTest() {
        authDAO.createAuth("AuthToken4", new AuthData("AuthToken4", "user4"));

        Assertions.assertEquals(authDAO.getAuthSize(), 4);
    }

    @Test
    void createAuthFailTest() {
        boolean thrown = false;
        try {
            authDAO.createAuth("AuthToken3", new AuthData("AuthToken3", "user4"));
        }
        catch (Exception e) {
            thrown = true;
        }
        Assertions.assertTrue(thrown);
    }

    @Test
    void getAuthTest() throws DataAccessException {
        AuthData one;
        try {
            one = authDAO.getAuth("AuthToken2");
        }
        catch (Exception e) {
            throw new DataAccessException("auth fail");
        }

        Assertions.assertEquals(one.username(), "user2");
    }

    @Test
    void getAuthFailTest() throws DataAccessException {
        AuthData one;
        boolean thrown = false;
        one = authDAO.getAuth("AuthToken5");

        Assertions.assertEquals(one, null);
    }


}
