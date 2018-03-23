package models.persistence;

import models.model.Item;
import models.persistence.jooq.JooqRepository;
import models.persistence.jooq.generated.tables.records.ItemRecord;
import play.db.Database;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static models.persistence.jooq.generated.Tables.ITEM;

public class JooqItemRepository extends JooqRepository implements ItemRepository {

    @Inject
    public JooqItemRepository(Database database) {
        super(database);
    }

    @Override
    public CompletionStage<Optional<Item>> findOne(Long id) {
        return supplyAsync(() -> Optional.ofNullable(
                create().selectFrom(ITEM)
                        .where(ITEM.ID.eq(id))
                        .fetchOneInto(Item.class)));
    }

    @Override
    public CompletionStage<List<Item>> findAll() {
        return supplyAsync(() -> create().selectFrom(ITEM)
                .fetch().into(Item.class));
    }

    @Override
    public CompletionStage<Item> insert(Item item) {
        return supplyAsync(() -> {
            ItemRecord record = create().insertInto(ITEM, ITEM.NAME, ITEM.PRICE)
                    .values(item.getName(), item.getPrice())
                    .returning(ITEM.ID)
                    .fetchOne();

            Long id = record.getId();
            item.setId(id);

            return item;
        });
    }

    @Override
    public CompletionStage<Item> update(Item item) {
        return supplyAsync(() -> {
            int updatedRows = create().update(ITEM)
                    .set(ITEM.NAME, item.getName())
                    .set(ITEM.PRICE, item.getPrice())
                    .where(ITEM.ID.eq(item.getId()))
                    .execute();

            if (updatedRows == 0) {
                return null;
            }

            return item;
        });
    }

    @Override
    public CompletionStage<Integer> delete(Long id) {
        return supplyAsync(() -> create().delete(ITEM)
                .where(ITEM.ID.eq(id))
                .execute());
    }

    @Override
    public CompletionStage<Integer> delete(Item item) {
        return delete(item.getId());
    }

    @Override
    public CompletionStage<Integer> deleteAll() {
        return supplyAsync(() -> create().delete(ITEM).execute());
    }
}