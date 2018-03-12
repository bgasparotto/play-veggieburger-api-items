package models.persistence.jooq;

import org.jooq.ConnectionProvider;
import org.jooq.exception.DataAccessException;
import play.Logger;
import play.db.Database;

import java.sql.Connection;
import java.sql.SQLException;

public class PlayConnectionProvider implements ConnectionProvider {
    private final Database database;
    private Connection connection = null;

    public PlayConnectionProvider(Database database) {
        this.database = database;
    }

    @Override
    public Connection acquire() throws DataAccessException {
        if (connection == null) {
            connection = database.getConnection();
        }
        return connection;
    }

    @Override
    public void release(Connection released) throws DataAccessException {
        if (this.connection != released) {
            String m = "Expected " + this.connection + " but got " + released;
            throw new IllegalArgumentException(m);
        }
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            Logger.error("Error closing connection " + connection, e);
        }
    }
}