package dataaccess;

import model.UserData;

public class SQLUserDAO implements UserDAOI{
    @Override
    public void clear() {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public boolean userFound(String username) {
        return false;
    }

    @Override
    public void putUser(String name, UserData user) {

    }

    @Override
    public int getUserSize() {
        return 0;
    }
}
