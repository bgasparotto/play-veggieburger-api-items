package models.persistence;

import models.model.Item;
import models.persistence.jooq.JooqRepository;
import models.persistence.jooq.generated.tables.records.ItemRecord;
import play.db.Database;

import javax.inject.Inject;
import java.util.List;

import static models.persistence.jooq.generated.Tables.ITEM;

public class JooqItemRepository extends JooqRepository implements ItemRepository {

    @Inject
    public JooqItemRepository(Database database) {
        super(database);
    }

    @Override
    public Item findOne(Long id) {
        Item item = create().selectFrom(ITEM)
                .where(ITEM.ID.eq(id))
                .fetchOneInto(Item.class);

        return item;
    }

    @Override
    public List<Item> findAll() {
        List<Item> all = create().selectFrom(ITEM).fetch().into(Item.class);
        return all;
    }

    @Override
    public Item insert(Item item) {
        ItemRecord record = create().insertInto(ITEM, ITEM.NAME, ITEM.PRICE)
                .values(item.getName(), item.getPrice())
                .returning(ITEM.ID)
                .fetchOne();

        Long id = record.getId();
        item.setId(id);

        return item;
    }

    @Override
    public Item update(Item item) {
        int updatedRows = create().update(ITEM)
                .set(ITEM.NAME, item.getName())
                .set(ITEM.PRICE, item.getPrice())
                .where(ITEM.ID.eq(item.getId()))
                .execute();

        if (updatedRows == 0) {
            return null;
        }

        return item;
    }

    @Override
    public int delete(Long id) {
        int deletedRows = create().delete(ITEM).where(ITEM.ID.eq(id)).execute();
        return deletedRows;
    }

    @Override
    public int delete(Item item) {
        return delete(item.getId());
    }

    @Override
    public void deleteAll() {
        create().delete(ITEM).execute();
    }
}