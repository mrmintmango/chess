package dataaccess;

import model.UserData;

import java.sql.SQLException;

public class SQLUserDAO implements UserDAOI{

    public SQLUserDAO() throws DataAccessException {
        DatabaseManager.createDatabase();
    }

    @Override
    public void clear() {
        var statement = "TRUNCATE user";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
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
        var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        try {
            DatabaseManager.executeUpdate(statement, name, user.password(), user.email());
        } catch (DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
    }

    public void registerUser(UserData user) {
        putUser(user.username(), user);
    }

    @Override
    public int getUserSize() {
        return 0;
    }
}
