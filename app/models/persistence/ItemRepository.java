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

    Long insert(Item item);

    Item update(Item item);

    void delete(Long id);

    void delete(Item item);
}