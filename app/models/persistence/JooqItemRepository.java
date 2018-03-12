package models.persistence;

import models.model.Item;
import play.db.Database;

import javax.inject.Inject;
import java.util.List;

public class JooqItemRepository extends JooqRepository implements ItemRepository {

    @Inject
    public JooqItemRepository(Database database) {
        super(database);
    }

    @Override
    public Item findOne(Long id) {
        return null;
    }

    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public Long insert(Item item) {
        return null;
    }

    @Override
    public Item update(Item item) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void delete(Item item) {

    }

    @Override
    public void clear() {

    }
}