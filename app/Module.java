import com.google.inject.AbstractModule;
import models.persistence.CollectionItemRepository;
import models.persistence.ItemRepository;

public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(ItemRepository.class).to(CollectionItemRepository.class)
                .asEagerSingleton();
    }
}