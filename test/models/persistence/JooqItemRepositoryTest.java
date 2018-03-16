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

    public void testShouldFindOne() {
        Item item = repository.findOne(1L);
        Assert.assertNotNull(item);
        Assert.assertEquals(1L, item.getId().longValue());
    }

    public void testShouldFindNullOnFindOnePassingNull() {
        Item item = repository.findOne(null);
        Assert.assertNull(item);
    }

    public void testShouldFindNullForNonExistingPositiveId() {
        Item item = repository.findOne(100L);
        Assert.assertNull(item);
    }

    public void testShouldFindNullForNegativeAndZeroIds() {
        Item forZero = repository.findOne(0L);
        Assert.assertNull(forZero);

        Item forMinusOne = repository.findOne(-1L);
        Assert.assertNull(forMinusOne);
    }

    public void testShouldFindAll() {
        List<Item> items = repository.findAll();
        Assert.assertEquals(5, items.size());
    }

    public void testShouldFindEmptyListWhenNoRecords() {
        repository.deleteAll();
        List<Item> items = repository.findAll();

        Assert.assertNotNull(items);
        Assert.assertEquals(0, items.size());
    }

    public void testShouldInsert() {
        Item item = new Item(null, "New Burger", new BigDecimal(9));
        Long insertedId = repository.insert(item);
        Assert.assertEquals(6L, insertedId.longValue());

        List<Item> items = repository.findAll();
        Assert.assertEquals(6, items.size());
    }

    public void testShouldUpdate() {
        Long updatingId = 1L;
        String newName = "New Expensive Burger";
        BigDecimal newPrice = new BigDecimal(99.00);
        Item item = new Item(updatingId, newName, newPrice);
        repository.update(item);

        Item updated = repository.findOne(updatingId);
        Assert.assertEquals(newName, updated.getName());
        Assert.assertEquals(newPrice.doubleValue(), updated.getPrice().doubleValue(), 2);
    }

    public void testShouldDeleteById() {
        Long deletingId = 5L;
        repository.delete(deletingId);

        Item item = repository.findOne(deletingId);
        Assert.assertNull(item);

        List<Item> items = repository.findAll();
        Assert.assertEquals(4, items.size());
    }

    public void testShouldDeleteItem() {
        Long deletingId = 5L;
        Item deletingItem = new Item();
        deletingItem.setId(deletingId);
        repository.delete(deletingItem);

        Item item = repository.findOne(deletingId);
        Assert.assertNull(item);

        List<Item> items = repository.findAll();
        Assert.assertEquals(4, items.size());
    }

    public void testShouldDeleteAll() {
        repository.deleteAll();
        List<Item> items = repository.findAll();
        Assert.assertEquals(0, items.size());
    }
}