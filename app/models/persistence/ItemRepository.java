package models.persistence;

import models.model.Item;

import java.util.List;

/**
 * Contract for the repository of items.
 *
 * @author Bruno Gasparotto
 */
public interface ItemRepository {

    Item findOne(Long id);

    List<Item> findAll();

    Item insert(Item item);

    Item update(Item item);

    int delete(Long id);

    int delete(Item item);

    void deleteAll();
}