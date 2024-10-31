package dataaccess;

import model.UserData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAOI{

    public SQLUserDAO() throws DataAccessException {
        DatabaseManager.createDatabase();
    }

    @Override
    public void clear() {
        var statement = "TRUNCATE user";
        DatabaseManager.clear(statement);
    }

    @Override
    public UserData getUser(String email) throws DataAccessException {
        var statement = "SELECT username, password, email FROM user WHERE email=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String user = rs.getString(1);
                        String pass = rs.getString(2);
                        String mail = rs.getString(3);
                        return new UserData(user, pass, mail);
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
        return null;
    }

    @Override
    public boolean userFound(String email) {
        var statement = "SELECT email FROM user WHERE email=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
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
        int size = 0;
        var statement = "SELECT email FROM user";
        try (var conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        size++;
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
        return size;
    }
}
