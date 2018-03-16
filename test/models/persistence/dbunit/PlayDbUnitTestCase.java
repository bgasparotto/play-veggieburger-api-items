package models.persistence.dbunit;

import play.db.Database;
import play.db.Databases;

import java.util.HashMap;
import java.util.Map;

public abstract class PlayDbUnitTestCase extends DbUnitTestCase {
    private Database database;

    public PlayDbUnitTestCase() {
        String driver = DbUnitParameters.DRIVER_CLASS.getValue();
        String url = DbUnitParameters.CONNECTION_URL.getValue();
        String username = DbUnitParameters.USERNAME.getValue();
        String password = DbUnitParameters.PASSWORD.getValue();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", username);
        parameters.put("password", password);

        this.database = Databases.createFrom(driver, url, parameters);
    }

    protected Database getDatabase() {
        return database;
    }
}