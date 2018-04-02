package models.persistence;

import models.model.Item;
import models.persistence.dbunit.PlayDbUnitTestCase;
import org.junit.Assert;
import org.junit.Before;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class JooqItemRepositoryTest extends PlayDbUnitTestCase {
    private ItemRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new JooqItemRepository(getDatabase());
        super.setUp();
    }

    public void testShouldFindOne() throws Exception {
        CompletionStage<Optional<Item>> result = repository.findOne(1L);
        Optional<Item> itemOptional = result.toCompletableFuture().get();
        Item item = itemOptional.get();

        Assert.assertNotNull(item);
        Assert.assertEquals(1L, item.getId().longValue());
    }

    public void testShouldFindNullOnFindOnePassingNull() throws Exception {
        CompletionStage<Optional<Item>> result = repository.findOne(null);
        Optional<Item> itemOptional = result.toCompletableFuture().get();
        Item item = itemOptional.orElse(null);

        Assert.assertNull(item);
    }

    public void testShouldFindNullForNonExistingPositiveId() throws Exception {
        CompletionStage<Optional<Item>> result = repository.findOne(100L);
        Optional<Item> itemOptional = result.toCompletableFuture().get();
        Item item = itemOptional.orElse(null);

        Assert.assertNull(item);
    }

    public void testShouldFindNullForNegativeAndZeroIds() throws Exception {
        CompletionStage<Optional<Item>> result = repository.findOne(0L);
        Optional<Item> itemOptional = result.toCompletableFuture().get();
        Item forZero = itemOptional.orElse(null);

        Assert.assertNull(forZero);

        result = repository.findOne(-1L);
        itemOptional = result.toCompletableFuture().get();
        Item forMinusOne = itemOptional.orElse(null);

        Assert.assertNull(forMinusOne);
    }

    public void testShouldFindAll() throws Exception {
        CompletionStage<List<Item>> result = repository.findAll();
        List<Item> items = result.toCompletableFuture().get();
        Assert.assertEquals(5, items.size());
    }

    public void testShouldFindEmptyListWhenNoRecords() throws Exception {
        repository.deleteAll().toCompletableFuture().get();
        CompletionStage<List<Item>> result = repository.findAll();
        List<Item> items = result.toCompletableFuture().get();

        Assert.assertNotNull(items);
        Assert.assertEquals(0, items.size());
    }

    public void testShouldInsert() throws Exception {
        Item item = new Item(null, "New Burger", new BigDecimal(9));
        CompletionStage<Item> result = repository.insert(item);
        Item createdItem = result.toCompletableFuture().get();

        Assert.assertEquals(6L, createdItem.getId().longValue());

        CompletionStage<List<Item>> findResult = repository.findAll();
        List<Item> items = findResult.toCompletableFuture().get();
        Assert.assertEquals(6, items.size());
    }

    public void testShouldUpdate() throws Exception {
        Long updatingId = 1L;
        String newName = "New Expensive Burger";
        BigDecimal newPrice = new BigDecimal(99.00);
        Item item = new Item(updatingId, newName, newPrice);
        repository.update(item).toCompletableFuture().get();

        CompletionStage<Optional<Item>> result = repository.findOne(updatingId);
        Optional<Item> itemOptional = result.toCompletableFuture().get();
        Item updated = itemOptional.get();

        Assert.assertEquals(newName, updated.getName());
        Assert.assertEquals(newPrice.doubleValue(), updated.getPrice().doubleValue(), 2);
    }

    public void testShouldDeleteById() throws Exception {
        Long deletingId = 5L;
        repository.delete(deletingId).toCompletableFuture().get();

        CompletionStage<Optional<Item>> result = repository.findOne(deletingId);
        Optional<Item> itemOptional = result.toCompletableFuture().get();
        Item item = itemOptional.orElse(null);

        Assert.assertNull(item);

        CompletionStage<List<Item>> findResult = repository.findAll();
        List<Item> items = findResult.toCompletableFuture().get();
        Assert.assertEquals(4, items.size());
    }

    public void testShouldDeleteItem() throws Exception {
        Long deletingId = 5L;
        Item deletingItem = new Item();
        deletingItem.setId(deletingId);
        repository.delete(deletingItem).toCompletableFuture().get();

        CompletionStage<Optional<Item>> result = repository.findOne(deletingId);
        Optional<Item> itemOptional = result.toCompletableFuture().get();
        Item item = itemOptional.orElse(null);
        Assert.assertNull(item);

        CompletionStage<List<Item>> findResult = repository.findAll();
        List<Item> items = findResult.toCompletableFuture().get();
        Assert.assertEquals(4, items.size());
    }

    public void testShouldDeleteAll() throws Exception {
        repository.deleteAll().toCompletableFuture().get();
        CompletionStage<List<Item>> findResult = repository.findAll();
        List<Item> items = findResult.toCompletableFuture().get();
        Assert.assertEquals(0, items.size());
    }
}