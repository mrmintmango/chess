package dataaccess;

import model.UserData;

public interface UserDAOI {

    void clear();

    UserData getUser(String username) throws DataAccessException;

    boolean userFound(String username);

    void putUser(String name, UserData user);

    int getUserSize();

    default void registerUser(UserData user) {

    }
}
