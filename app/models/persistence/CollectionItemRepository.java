package models.persistence;

import models.model.Item;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of {@link ItemRepository} for testing purposes.
 */
public class CollectionItemRepository implements ItemRepository {
    private List<Item> items;

    public CollectionItemRepository() {
        items = new ArrayList<>();
    }

    @Override
    public Item findOne(Long id) {
        return items.stream().filter(i -> i.getId() == id).findFirst().get();
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
}