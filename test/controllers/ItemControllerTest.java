package controllers;

import models.model.Item;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

public class ItemControllerTest extends WithApplication {
    private Http.RequestBuilder request;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Before
    public void setUp() {
        this.request = new Http.RequestBuilder().uri("/api/items");
    }

    @Test
    public void shouldReturnOkWhenGetExistingResource() {
        request = request.method(GET)
                .uri(request.uri() + "/1");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void shouldReturnNotFoundWhenGetUnexistingResource() {
        request = request.method(GET)
                .uri(request.uri() + "/100");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

    @Test
    public void shouldReturnOkWhenGetAllResources() {
        request = request.method(GET);
        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void shouldReturnCreatedWhenPostResource() {
        Item item = new Item("New Burger", new BigDecimal(9));
        request = request.method(POST)
                .bodyJson(Json.toJson(item));

        Result result = route(app, request);
        assertEquals(CREATED, result.status());
    }

    @Test
    public void shouldReturnBadRequestWhenPostResourceWithId() {
        Item item = new Item(1L,"New Burger", new BigDecimal(9));
        request = request.method(POST)
                .bodyJson(Json.toJson(item));

        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void shouldReturnOKWhenPutResource() {
        Item item = new Item("New Burger", new BigDecimal(9));
        request = request.method(PUT)
                .uri(request.uri() + "/1")
                .bodyJson(Json.toJson(item));

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void shouldReturnNotFoundWhenPutUnexistingResource() {
        Item item = new Item("New Burger", new BigDecimal(9));
        request = request.method(PUT)
                .uri(request.uri() + "/100")
                .bodyJson(Json.toJson(item));

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

    @Test
    public void shouldReturnOkWhenPatchResource() {
        Item item = new Item();
        item.setPrice(new BigDecimal(99));
        request = request.method("PATCH")
                .uri(request.uri() + "/1")
                .bodyJson(Json.toJson(item));

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void shouldReturnNotFoundWhenPatchUnexistingResource() {
        Item item = new Item();
        item.setPrice(new BigDecimal(99));
        request = request.method("PATCH")
                .uri(request.uri() + "/100")
                .bodyJson(Json.toJson(item));

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

    @Test
    public void shouldReturnOkWhenDeleteResource() {
        request = request.method(DELETE)
                .uri(request.uri() + "/1");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void shouldReturnNotFoundWhenDeleteUnexistingResource() {
        request = request.method(DELETE)
                .uri(request.uri() + "/100");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

    @Test
    public void shouldReturnOkWhenDeleteAllResources() {
        request = request.method(DELETE);

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }
}