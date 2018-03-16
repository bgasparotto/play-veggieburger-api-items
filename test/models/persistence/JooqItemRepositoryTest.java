package models.persistence;

import models.model.Item;
import models.persistence.dbunit.PlayDbUnitTestCase;
import org.junit.Assert;
import org.junit.Before;

import java.math.BigDecimal;
import java.util.List;

public class JooqItemRepositoryTest extends PlayDbUnitTestCase {
    private ItemRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new JooqItemRepository(getDatabase());
        super.setUp();
    }

    public void testFindOne() {
        Item item = repository.findOne(1L);
        Assert.assertNotNull(item);
        Assert.assertEquals(1L, item.getId().longValue());
    }
    public void testFindAll() {
        List<Item> items = repository.findAll();
        Assert.assertEquals(5, items.size());
    }

    public void testInsert() {
        Item item = new Item(null, "New Burger", new BigDecimal(9));
        Long insertedId = repository.insert(item);
        Assert.assertEquals(6L, insertedId.longValue());

        List<Item> items = repository.findAll();
        Assert.assertEquals(6, items.size());
    }

    public void testUpdate() {
        Long updatingId = 1L;
        String newName = "New Expensive Burger";
        BigDecimal newPrice = new BigDecimal(99.00);
        Item item = new Item(updatingId, newName, newPrice);
        repository.update(item);

        Item updated = repository.findOne(updatingId);
        Assert.assertEquals(newName, updated.getName());
        Assert.assertEquals(newPrice.doubleValue(), updated.getPrice().doubleValue(), 2);
    }

    public void testDeleteById() {
        Long deletingId = 5L;
        repository.delete(deletingId);

        Item item = repository.findOne(deletingId);
        Assert.assertNull(item);

        List<Item> items = repository.findAll();
        Assert.assertEquals(4, items.size());
    }

    public void testDeleteItem() {
        Long deletingId = 5L;
        Item deletingItem = new Item();
        deletingItem.setId(deletingId);
        repository.delete(deletingItem);

        Item item = repository.findOne(deletingId);
        Assert.assertNull(item);

        List<Item> items = repository.findAll();
        Assert.assertEquals(4, items.size());
    }

    public void testClear() {
        repository.deleteAll();
        List<Item> items = repository.findAll();
        Assert.assertEquals(0, items.size());
    }
}