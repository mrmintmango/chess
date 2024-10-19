package dataaccess;

import model.UserData;

public interface UserDAOI {

    public void clear();

    public void createUser(String username, String password, String email);

    public UserData getUser(String username) throws DataAccessException;

    public void registerUser(UserData user);
}
