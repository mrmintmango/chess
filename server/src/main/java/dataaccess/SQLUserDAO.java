package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

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
    public UserData getUser(String username) throws DataAccessException {
        var statement = "SELECT username, password, email FROM user WHERE username=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String user = rs.getString(1);
                        String pass = rs.getString(2);
                        String mail = rs.getString(3);
                        return new UserData(user, pass, mail);
                    }
                    else {
                        throw new DataAccessException("username not found");
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Bad connection"); //update later
        }
    }

    @Override
    public boolean userFound(String username) {
        var statement = "SELECT username FROM user WHERE username=?";
        return DatabaseManager.found(statement, username);
    }

    @Override
    public void putUser(String name, UserData user) {
        var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        try {
            //Hash the password first for protection.
            String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());

            DatabaseManager.executeUpdate(statement, name, hashedPassword, user.email());
        } catch (DataAccessException e) {
            throw new RuntimeException(e); //update later
        }
    }

    public void registerUser(UserData user) {
        putUser(user.username(), user);
    }

    @Override
    public int getUserSize() {
        var statement = "SELECT email FROM user";
        return DatabaseManager.size(statement);
    }
}
