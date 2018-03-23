package models.persistence;

import models.model.Item;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * Contract for the repository of items.
 *
 * @author Bruno Gasparotto
 */
public interface ItemRepository {

    CompletionStage<Optional<Item>> findOne(Long id);

    CompletionStage<List<Item>> findAll();

    CompletionStage<Item> insert(Item item);

    CompletionStage<Item> update(Item item);

    CompletionStage<Integer> delete(Long id);

    CompletionStage<Integer> delete(Item item);

    CompletionStage<Integer> deleteAll();
}