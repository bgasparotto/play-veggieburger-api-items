package models.persistence;

import models.model.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of {@link ItemRepository} for testing purposes.
 */
public class CollectionItemRepository implements ItemRepository {
    private List<Item> items;

    public CollectionItemRepository() {
        items = new ArrayList<>();
        items.add(new Item(1l, "Small Veggie Burger", new BigDecimal(7)));
        items.add(new Item(2l, "Medium Veggie Burger", new BigDecimal(9)));
        items.add(new Item(3l, "Large Veggie Burger", new BigDecimal(11)));
    }

    @Override
    public Item findOne(Long id) {
        return items.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Item> findAll() {
        return items;
    }

    @Override
    public Long insert(Item item) {
        Long id = item.getId();
        if (id == null) {
            id = items.stream().mapToLong(Item::getId).max().orElse(0l);
            id++;
            item.setId(id);
        }

        items.add(item);
        return id;
    }

    @Override
    public Item update(Item item) {
        items.remove(item);
        items.add(item);

        return item;
    }

    @Override
    public void delete(Long id) {
        Item item = items.stream().filter(i -> i.getId() == id).findFirst().get();
        items.remove(item);
    }

    @Override
    public void delete(Item item) {
        items.remove(item);
    }

    @Override
    public void clear() {
        items.clear();
    }
}