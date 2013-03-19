package com.agile.spirit.openapi;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agile.spirit.openapi.domain.Note;
import com.agile.spirit.openapi.domain.NoteFactory;
import com.agile.spirit.openapi.domain.events.EventStore;
import com.agile.spirit.openapi.domain.events.LoggingEventHandler;
import com.agile.spirit.openapi.utils.hibernate.PersistenceUtil;
import com.agile.spirit.openapi.utils.jersey.CorsResponseFilter;
import com.google.common.eventbus.EventBus;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class Main {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

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

    private final static String RESOURCES_PACKAGES = "com.agile.spirit.openapi.resources";

    protected static HttpServer startServer(URI uri) throws IOException {
        LOGGER.info("Instances a new ResourceConfig on package " + RESOURCES_PACKAGES);
        ResourceConfig rc = new PackagesResourceConfig(RESOURCES_PACKAGES);

        // UNCOMMENT IN ORDER TO ADD LOGGING FILTER & HTTP BASIC FILTER
        /*
         * LOGGER.info("Adds Jersey container Request filters");
         * rc.getProperties().put(
         * "com.sun.jersey.spi.container.ContainerRequestFilters",
         * "com.sun.jersey.api.container.filter.LoggingFilter;" +
         * AuthenticationRequestFilter.class.getCanonicalName());
         */

        LOGGER.info("Adds Jersey container Response filters");
        String responseFilters = "" + CorsResponseFilter.class.getCanonicalName();
        rc.getProperties().put("com.sun.jersey.spi.container.ContainerResponseFilters", responseFilters);

        LOGGER.info("Creates an HttpServer on URI " + uri);
        HttpServer server = GrizzlyServerFactory.createHttpServer(uri, rc);

        return server;
    }

    public static void main(String[] args) throws IOException {
        initializeEventStore();

        PersistenceUtil.createEntityManagerFactory();
        addNotes();

        String hostname = System.getenv("HOSTNAME");
        if (hostname == null) {
            hostname = "localhost";
        }
        LOGGER.info("HOSTNAME = " + hostname);

        boolean isOnLocal = false;
        String port = System.getenv("PORT");
        LOGGER.info("PORT = " + port);
        if (port == null) {
            isOnLocal = true;
            port = "9998";
        }

        URI uri = getBaseURI(hostname, Integer.valueOf(port));

        LOGGER.info("Starting grizzly...");
        HttpServer httpServer = startServer(uri);
        LOGGER.info(String.format("Jersey app started with WADL available at "
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
