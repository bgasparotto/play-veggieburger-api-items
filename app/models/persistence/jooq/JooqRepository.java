package models.persistence.jooq;

import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import play.db.Database;

import java.util.Objects;

/**
 * Abstraction for JOOQ repositories providing a DSLContext for query building.
 *
 * @author Bruno Gasparotto
 */
public abstract class JooqRepository {
    private final Database database;

    /**
     * Constructor.
     *
     * @param database The database instance where the connections will be
     *                 obtained from. Can't be null
     */
    public JooqRepository(Database database) {
        Objects.requireNonNull(database, "Database can't be null.");
        this.database = database;
    }

    /**
     * Create a {@code DSLContexts} to be used as basis for writing SQL.
     *
     * @return A initialised {@code DSLContexts}
     */
    protected DSLContext create() {
        ConnectionProvider provider = new PlayConnectionProvider(database);
        DSLContext dslContext = DSL.using(provider, SQLDialect.H2);
        return dslContext;
    }
}