package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccess;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class ParentService {
    public DataAccess dataAccess = new DataAccess();
    public GameDAO gameDAO = new GameDAO();
    public UserDAO userDAO = new UserDAO();
    public AuthDAO authDAO = new AuthDAO();

    public void ClearApplication() {
        dataAccess.clear();
    }
}
