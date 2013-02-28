package com.agile.spirit.openapi;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.agile.spirit.openapi.utils.PersistenceUtil;

/*
 * 1. All our resources are accessible via the URL "http://localhost:9998/notes/Xxx"
 * 2. We use a GenericEntity in order to marshal the JSON list from the Java beans.
 */

@Path("/notes")
public class NotesResource {

    private EntityManager em = PersistenceUtil.getEntityManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listNotes() {
        List<Note> notes = Note.list(em);
        GenericEntity<List<Note>> entity = new GenericEntity<List<Note>>(notes) {
        };
        return Response.ok(entity).build();
    }

    @GET
    @Path("/search/{pattern}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listNotes(@PathParam("pattern") String pattern) {
        List<Note> notes = Note.find(pattern, em);
        GenericEntity<List<Note>> entity = new GenericEntity<List<Note>>(notes) {
        };
        return Response.ok(entity).build();
    }

    @GET
    @Path("/{noteId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNote(@PathParam("noteId") Integer noteId) {
        if (noteId != null) {
            Note note = Note.get(noteId, em);
            return Response.ok(note).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/")
    // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNote(Note note) {
        if (note != null) {
            Note persisted = Note.save(note, em);
            if (persisted != null) {
                return Response.ok(persisted).build();
            }
        }
        System.err.println("note is null !");
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNote(Note note) {
        if (note != null) {
            Note merged = Note.save(note, em);
            if (merged != null) {
                return Response.ok(merged).build();
            }
        }
        System.err.println("note is null !");
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("/{noteId}")
    public Response deleteNote(@PathParam("noteId") Integer noteId) {
        if (noteId != null) {
            Note.delete(noteId, em);
            return Response.ok().build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

}
