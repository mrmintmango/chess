package dataaccess;

import model.UserData;

public interface UserDAOI {

    void clear();

    void createUser(String username, String password, String email);

    UserData getUser(String username) throws DataAccessException;

    public boolean userFound(String username);

    public void putUser(String name, UserData user);

    default void registerUser(UserData user) {

    }
}
