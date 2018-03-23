package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.model.Item;
import models.persistence.ItemRepository;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * REST API controller for the Item resource.
 *
 * @author Bruno Gasparotto
 */
public class ItemController extends Controller {
    private ItemRepository repository;
    private HttpExecutionContext executionContext;

    @Inject
    public ItemController(ItemRepository repository,
                          HttpExecutionContext executionContext) {

        this.repository = repository;
        this.executionContext = executionContext;
    }

    /**
     * Views a resource by its {@code id}.
     *
     * @param id The {@code id} of the resource to be viewed
     * @return The resource corresponding to the given {@code id}
     */
    public CompletionStage<Result> view(Long id) {
        return repository.findOne(id)
                .thenApplyAsync(itemOptional -> itemOptional
                                .map(i -> ok(Json.toJson(i)))
                                .orElseGet(Results::notFound),
                        executionContext.current());
    }

    /**
     * Lists all the available resources.
     *
     * @return The collection of all the available resources, or an empty
     * collection if no resources exist
     */
    public CompletionStage<Result> list() {
        return repository.findAll()
                .thenApplyAsync(items -> ok(Json.toJson(items)),
                        executionContext.current());
    }

    /**
     * Create a new resource.
     *
     * @return The created resource
     */
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        Item item = Json.fromJson(json, Item.class);

        if (Objects.nonNull(item.getId())) {
            return supplyAsync(() -> {
                String message = "Resouce id must not be provided.";
                return badRequest(Json.toJson(message));
            }, executionContext.current());
        }

        return repository.insert(item)
                .thenApplyAsync(createdItem -> created(Json.toJson(createdItem)),
                        executionContext.current());
    }

    /**
     * Updates the whole resource with the given input parameters.
     *
     * @param id The {@code id} of the resource to be updated
     * @return The updated resource
     */
    public CompletionStage<Result> update(Long id) {
        JsonNode json = request().body().asJson();
        Item item = Json.fromJson(json, Item.class);
        item.setId(id);

        return repository.update(item).thenApplyAsync(updatedItem -> {
            Optional<Item> o = Optional.ofNullable(updatedItem);
            return o.map(i -> ok(Json.toJson(i))).orElseGet(Results::notFound);
        }, executionContext.current());
    }

    /**
     * Partially modifies a resource according to the given parameters.
     *
     * @param id The {@code id} of the resource to be modified
     * @return The modified resource
     */
    public CompletionStage<Result> modify(Long id) {
        JsonNode json = request().body().asJson();
        Item item = Json.fromJson(json, Item.class);

        return repository.findOne(id).thenApplyAsync(itemOptional -> {
            itemOptional.map((existing) -> {
                Optional.ofNullable(item.getName()).ifPresent(existing::setName);
                Optional.ofNullable(item.getPrice()).ifPresent(existing::setPrice);

                return repository.update(existing)
                        .thenApplyAsync(updatedItem -> ok(Json.toJson(updatedItem)));
            }).orElseGet(supplyAsync(() -> Results::notFound));
        });
    }

    /**
     * Removes a resource by its {@code id}.
     *
     * @param id The {@code id} of the resource to be removed
     * @return The {@code OK} HTTP status code
     */
    public CompletionStage<Result> remove(Long id) {
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
    public CompletionStage<Result> removeAll() {
        return repository.deleteAll()
                .thenApplyAsync((i) -> ok(),
                        executionContext.current());
    }
}