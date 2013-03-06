package com.agile.spirit.openapi;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.agile.spirit.openapi.events.EventStore;
import com.agile.spirit.openapi.events.LoggingEventHandler;
import com.agile.spirit.openapi.utils.PersistenceUtil;
import com.google.common.eventbus.EventBus;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class Main {

    private static void initializeEventStore() {
        EventBus eventBus = EventStore.getEventBus();
        LoggingEventHandler eventHandler = new LoggingEventHandler();
        eventBus.register(eventHandler);
    }

    private static void addNotes() {
        EntityManager em = PersistenceUtil.getEntityManager();
        List<Note> notes = NoteFactory.createListOfNotes(10);
        for (Note note : notes) {
            Note.save(note, em);
        }
    }

    private static URI getBaseURI(String hostname, int port) {
        return UriBuilder.fromUri("http://0.0.0.0/").port(port).build();
    }

    protected static HttpServer startServer(URI uri) throws IOException {
        System.out.println("Starting grizzly...");
        ResourceConfig rc = new PackagesResourceConfig("com.agile.spirit.openapi");
        return GrizzlyServerFactory.createHttpServer(uri, rc);
    }

    public static void main(String[] args) throws IOException {
        initializeEventStore();

        PersistenceUtil.createEntityManagerFactory();
        addNotes();

        String hostname = System.getenv("HOSTNAME");
        if (hostname == null) {
            hostname = "localhost";
        }
        System.out.println("HOSTNAME = " + hostname);

        boolean isOnLocal = false;
        String port = System.getenv("PORT");
        System.out.println("PORT = " + port);
        if (port == null) {
            isOnLocal = true;
            port = "9998";
        }

        URI uri = getBaseURI(hostname, Integer.valueOf(port));
        System.out.println("URI = " + uri);

        HttpServer httpServer = startServer(uri);
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", uri, uri));
        if (isOnLocal) {
            System.in.read();
            httpServer.stop();
            PersistenceUtil.closeEntityManagerFactory();
        } else {
            while (true) {
                System.in.read();
            }
        }

    }
}
