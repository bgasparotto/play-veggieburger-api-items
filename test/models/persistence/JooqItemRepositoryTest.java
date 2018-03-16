package models.persistence;

import models.model.Item;
import models.persistence.dbunit.DbUnitParameters;
import models.persistence.dbunit.DbUnitTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import play.db.Database;
import play.db.Databases;

import java.util.HashMap;
import java.util.Map;

public class JooqItemRepositoryTest extends DbUnitTestCase {
    private ItemRepository repository;

    @Before
    public void setUp() throws Exception {
        String driver = DbUnitParameters.DRIVER_CLASS.getValue();
        String url = DbUnitParameters.CONNECTION_URL.getValue();
        String username = DbUnitParameters.USERNAME.getValue();
        String password = DbUnitParameters.PASSWORD.getValue();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", username);
        parameters.put("password", password);

        Database database = Databases.createFrom(driver, url, parameters);
        repository = new JooqItemRepository(database);

        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindOne() {
        Item item = repository.findOne(1L);
        Assert.assertNotNull(item);
    }

    @Test
    public void findAll() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void delete1() {
    }

    @Test
    public void clear() {
    }
}