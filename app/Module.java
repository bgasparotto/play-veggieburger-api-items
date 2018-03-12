import com.google.inject.AbstractModule;
import models.persistence.CollectionItemRepository;
import models.persistence.ItemRepository;

/**
 * Module which declares the bindings between interfaces and their respective
 * implementations.
 *
 * @author Bruno Gasparotto
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(ItemRepository.class).to(CollectionItemRepository.class)
                .asEagerSingleton();
    }
}