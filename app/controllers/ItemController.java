package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.model.Item;
import models.persistence.ItemRepository;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST API controller for the Item resource.
 *
 * @author Bruno Gasparotto
 */
public class ItemController extends Controller {
    private ItemRepository repository;

    @Inject
    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    public Result view(Long id) {
        Item item = repository.findOne(id);
        Optional<Item> o = Optional.ofNullable(item);

        return o.map(i -> ok(Json.toJson(i))).orElseGet(Results::notFound);
    }

    public Result list() {
        List<Item> items = repository.findAll();
        return ok(Json.toJson(items));
    }

    public Result create() {
        JsonNode json = request().body().asJson();
        Item item = Json.fromJson(json, Item.class);

        Long id = repository.insert(item);
        return created(Json.toJson(id));
    }

    public Result update(Long id) {
        JsonNode json = request().body().asJson();
        Item item = Json.fromJson(json, Item.class);
        item.setId(id);

        Item updated = repository.update(item);
        return ok(Json.toJson(updated));
    }

    public Result modify(Long id) {
        JsonNode json = request().body().asJson();
        Item item = Json.fromJson(json, Item.class);
        Item existing = repository.findOne(id);

        Optional.ofNullable(item.getName()).ifPresent(existing::setName);
        Optional.ofNullable(item.getPrice()).ifPresent(existing::setPrice);

        Item updated = repository.update(existing);
        return ok(Json.toJson(updated));
    }

    public Result remove(Long id) {
        repository.delete(id);
        return ok();
    }

    public Result removeAll() {
        repository.deleteAll();
        return ok();
    }
}