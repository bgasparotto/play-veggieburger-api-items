import com.google.inject.AbstractModule;
import models.persistence.ItemRepository;
import models.persistence.JooqItemRepository;

/**
 * Module which declares the bindings between interfaces and their respective
 * implementations.
 *
 * @author Bruno Gasparotto
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(ItemRepository.class).to(JooqItemRepository.class)
                .asEagerSingleton();
    }
}