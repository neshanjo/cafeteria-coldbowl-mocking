package de.schneider21.mensacoldbowl;

import java.io.IOException;

import de.schneider21.mensacoldbowl.framework.HttpServer;
import de.schneider21.mensacoldbowl.meal.api.MealController;
import fi.iki.elonen.NanoHTTPD;

public class Application {

    public static void main(String[] args) throws IOException {

        ServiceFactory.setProfile(ServiceFactory.Profile.LOCAL);
        final HttpServer httpServer = new HttpServer(8080);
        httpServer.registerRestController(new MealController());
        httpServer.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("Application: Server running on localhost:8080/...");
    }
}
