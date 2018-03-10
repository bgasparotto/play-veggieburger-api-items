package controllers;

import play.mvc.*;

public class ItemController extends Controller {

    public Result view() {
        return ok("addresses");
    }
}