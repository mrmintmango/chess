package dataaccess;

import org.junit.jupiter.api.BeforeEach;

public class SQLUserDAOTests {
    UserDAOI userDAO = new SQLUserDAO();

    public SQLUserDAOTests() throws DataAccessException {
    }

    @BeforeEach
    public void setUserDAO() {

    }
}
