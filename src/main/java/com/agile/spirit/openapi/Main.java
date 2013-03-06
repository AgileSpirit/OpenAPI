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
        PersistenceUtil.createEntityManagerFactory();
        addNotes();

        String hostname = System.getenv("HOSTNAME");
        if (hostname == null) {
            hostname = "localhost";
        }
        System.out.println("HOSTNAME = " + hostname);

        String port = System.getenv("PORT");
        System.out.println("PORT = " + port);
        if (port == null) {
            port = "9998";
        }

        URI uri = getBaseURI(hostname, Integer.valueOf(port));
        System.out.println("URI = " + uri);

        HttpServer httpServer = startServer(uri);
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", uri, uri));
        System.in.read();
        httpServer.stop();
        PersistenceUtil.closeEntityManagerFactory();
    }
}
