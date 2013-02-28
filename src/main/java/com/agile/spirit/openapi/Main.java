package com.agile.spirit.openapi;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.agile.spirit.openapi.utils.PersistenceUtil;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class Main {

    private static EntityManager em;

    private static void addNotes() {
        List<Note> notes = NoteFactory.createListOfNotes(10);
        for (Note note : notes) {
            Note.save(note, em);
        }
    }

    private static int getPort(int defaultPort) {
        String port = System.getProperty("jersey.test.port");
        if (null != port) {
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException e) {
            }
        }
        return defaultPort;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://openapi.herokuapp.com/").port(getPort(9998)).build();
    }

    public static final URI BASE_URI = getBaseURI();

    protected static HttpServer startServer() throws IOException {
        System.out.println("Starting grizzly...");
        ResourceConfig rc = new PackagesResourceConfig("com.agile.spirit.openapi");
        return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
    }

    public static void main(String[] args) throws IOException {
        PersistenceUtil.createEntityManagerFactory();
        em = PersistenceUtil.getEntityManager();

        addNotes();

        HttpServer httpServer = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI, BASE_URI));
        System.in.read();
        httpServer.stop();

        PersistenceUtil.closeEntityManagerFactory();
    }
}
