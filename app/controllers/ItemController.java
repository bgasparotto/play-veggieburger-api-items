package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.model.Item;
import models.persistence.ItemRepository;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
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

    /**
     * Views a resource by its {@code id}.
     *
     * @param id The {@code id} of the resource to be viewed
     * @return The resource corresponding to the given {@code id}
     */
    public Result view(Long id) {
        Item item = repository.findOne(id);
        Optional<Item> o = Optional.ofNullable(item);

        return o.map(i -> ok(Json.toJson(i))).orElseGet(Results::notFound);
    }

    /**
     * Lists all the available resources.
     *
     * @return The collection of all the available resources, or an empty
     * collection if no resources exist
     */
    public Result list() {
        List<Item> items = repository.findAll();
        return ok(Json.toJson(items));
    }

    /**
     * Create a new resource.
     *
     * @return The created resource
     */
    public Result create() {
        JsonNode json = request().body().asJson();
        Item item = Json.fromJson(json, Item.class);

        if (Objects.nonNull(item.getId())) {
            String message = "Resouce id must not be provided.";
            return badRequest(Json.toJson(message));
        }

        Item createdItem = repository.insert(item);
        return created(Json.toJson(createdItem));
    }

    /**
     * Updates the whole resource with the given input parameters.
     *
     * @param id The {@code id} of the resource to be updated
     * @return The updated resource
     */
    public Result update(Long id) {
        JsonNode json = request().body().asJson();
        Item item = Json.fromJson(json, Item.class);
        item.setId(id);

        Item updatedItem = repository.update(item);
        Optional<Item> o = Optional.ofNullable(updatedItem);
        return o.map(i -> ok(Json.toJson(i))).orElseGet(Results::notFound);
    }

    /**
     * Partially modifies a resource according to the given parameters.
     *
     * @param id The {@code id} of the resource to be modified
     * @return The modified resource
     */
    public Result modify(Long id) {
        JsonNode json = request().body().asJson();
        Item item = Json.fromJson(json, Item.class);
        Item existing = repository.findOne(id);

        if (existing == null) {
            return notFound();
        }

        Optional.ofNullable(item.getName()).ifPresent(existing::setName);
        Optional.ofNullable(item.getPrice()).ifPresent(existing::setPrice);

        Item updatedItem = repository.update(existing);
        return ok(Json.toJson(updatedItem));
    }

    /**
     * Removes a resource by its {@code id}.
     *
     * @param id The {@code id} of the resource to be removed
     * @return The {@code OK} HTTP status code
     */
    public Result remove(Long id) {
        int deletedResources = repository.delete(id);
        if (deletedResources == 0) {
            return notFound();
        }

        return ok();
    }

    /**
     * Removes all resources.
     *
     * @return The {@code OK} HTTP status code
     */
    public Result removeAll() {
        repository.deleteAll();
        return ok();
    }
}